package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.mapper.EventMapper;
import project.community.theatre.model.EventEntity;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.service.ImageUploadService;
import project.community.theatre.service.EventService;
import project.community.theatre.util.CustomMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ImageUploadService imageUploadService;

    @Override
    public EventResponseDto addEvent(EventEntryDto eventEntryDto) {
        log.info("Adding a new event :: {}", eventEntryDto);
        try {
            String imageUrl = processAndStoreImage(eventEntryDto.getImage());

            EventEntity eventEntity = EventMapper.mapDtoToEntity(eventEntryDto);
            eventEntity.setImageUrl(imageUrl);

            eventRepository.save(eventEntity);
            log.info("Event added successfully :: {}", eventEntity);
            return EventMapper.mapEntityToDto(eventEntity);
        } catch (Exception e) {
            log.error("Error while adding event :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add event", e);
        }
    }

    @Override
    public EventResponseDto getEvent(int id) {
        log.info("Fetching movie by ID :: {}", id);
        try {
            return eventRepository.findById(id)
                    .map(EventMapper::mapEntityToDto)
                    .orElseThrow(() -> {
                        log.error("Movie not found with ID :: {}", id);
                        return new RuntimeException("Movie not found with ID: " + id);
                    });
        } catch (Exception e) {
            log.error("Error while fetching movie by ID :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch movie", e);
        }
    }

    @Override
    public List<EventResponseDto> getAllEvent() {
        log.info("Fetching all event");
        try {
            List<EventEntity> movieEntities = eventRepository.findAll();
            if (movieEntities.isEmpty()) {
                log.warn("No event found in the database");
            }
            return movieEntities.stream()
                    .map(EventMapper::mapEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching all event :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all event", e);
        }
    }


    private String processAndStoreImage(MultipartFile image) {
        File tempFile = null;
        try {
            // Validate the image
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("Only image files are allowed");
            }

            if (image.getSize() > 5 * 1024 * 1024) { // 5MB limit
                throw new IllegalArgumentException("File size exceeds the limit of 5MB");
            }

            // Generate a unique filename for temporary storage
            String fileName = UUID.randomUUID() + "." + getExtension(image.getOriginalFilename());
            tempFile = File.createTempFile("temp-", "-" + fileName); // Create a temp file

            // Log details for debugging
            log.info("Processing image: {}", image.getOriginalFilename());
            log.info("Temporary file path: {}", tempFile.getAbsolutePath());

            // Resize and compress the image locally
            Thumbnails.of(image.getInputStream())
                    .size(800, 600)
                    .outputQuality(0.8)
                    .toFile(tempFile);

            // Convert the processed file back to MultipartFile for ImageUploadService
            MultipartFile processedImage = new CustomMultipartFile(tempFile, image.getOriginalFilename());

            // Upload to Cloudinary using ImageUploadService
            String imageUrl = imageUploadService.uploadImage(processedImage);
            log.info("Uploaded to Cloudinary: {}", imageUrl);

            return imageUrl;

        } catch (IllegalArgumentException e) {
            log.error("Validation failed: {}", e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error("Failed to process or upload the image", e);
            throw new RuntimeException("Failed to process or upload the image", e);
        } finally {
            // Clean up the temporary file
            if (tempFile != null && tempFile.exists()) {
                try {
                    Files.delete(tempFile.toPath());
                } catch (IOException e) {
                    log.warn("Failed to delete temporary file: {}", tempFile.getAbsolutePath(), e);
                }
            }
        }
    }

    private String getExtension(String originalFilename) {
        return Optional.ofNullable(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1))
                .orElseThrow(() -> new IllegalArgumentException("Invalid file format"));
    }
}