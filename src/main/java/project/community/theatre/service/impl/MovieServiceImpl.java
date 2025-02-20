package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.mapper.MovieConverter;
import project.community.theatre.dto.requestDto.MovieEntryDto;
import project.community.theatre.dto.responseDto.MovieResponseDto;
import project.community.theatre.model.MovieEntity;
import project.community.theatre.repository.MovieRepository;
import project.community.theatre.service.MovieService;
import net.coobird.thumbnailator.Thumbnails;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieResponseDto addMovie(MovieEntryDto movieEntryDto) {
        log.info("Adding a new movie :: {}", movieEntryDto);
        try {
            String imageUrl = processAndStoreImage(movieEntryDto.getImage());

            MovieEntity movieEntity = MovieConverter.mapDtoToEntity(movieEntryDto);
            movieEntity.setImageUrl(imageUrl);

            movieRepository.save(movieEntity);
            log.info("Movie added successfully :: {}", movieEntity);
            return MovieConverter.mapEntityToDto(movieEntity);
        } catch (Exception e) {
            log.error("Error while adding movie :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add movie", e);
        }
    }

    @Override
    public MovieResponseDto getMovie(int id) {
        log.info("Fetching movie by ID :: {}", id);
        try {
            return movieRepository.findById(id)
                    .map(MovieConverter::mapEntityToDto)
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
    public List<MovieResponseDto> getAllMovies() {
        log.info("Fetching all movies");
        try {
            List<MovieEntity> movieEntities = movieRepository.findAll();
            if (movieEntities.isEmpty()) {
                log.warn("No movies found in the database");
            }
            return movieEntities.stream()
                    .map(MovieConverter::mapEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching all movies :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all movies", e);
        }
    }


    private String processAndStoreImage(MultipartFile image) {
        try {
            // Validate the image
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("Only image files are allowed");
            }

            if (image.getSize() > 5 * 1024 * 1024) { // 5MB limit
                throw new IllegalArgumentException("File size exceeds the limit of 5MB");
            }

            // Generate a unique filename
            String fileName = UUID.randomUUID() + "." + getExtension(image.getOriginalFilename());
            Path uploadDir = Paths.get("uploads/images/");
            Files.createDirectories(uploadDir); // Ensure the directory exists
            Path filePath = uploadDir.resolve(fileName);

            // Log details for debugging
            log.info("Processing image: {}", image.getOriginalFilename());
            log.info("Generated file path: {}", filePath);

            // Resize and compress the image
            Thumbnails.of(image.getInputStream())
                    .size(800, 600)
                    .outputQuality(0.8)
                    .toFile(filePath.toFile());

            return filePath.toString();
        } catch (IllegalArgumentException e) {
            log.error("Validation failed: {}", e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error("Failed to process the image", e);
            throw new RuntimeException("Failed to process the image", e);
        }
    }

    private String getExtension(String originalFilename) {
        return Optional.ofNullable(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1))
                .orElseThrow(() -> new IllegalArgumentException("Invalid file format"));
    }
}