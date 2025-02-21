package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.exceptionHandler.InvalidImageException;
import project.community.theatre.mapper.EventMapper;
import project.community.theatre.model.EventEntity;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.service.EventService;
import project.community.theatre.service.ImageService;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public EventResponseDto addEvent(EventEntryDto eventEntryDto) {
        log.info("Adding a new event :: {}", eventEntryDto);
        try {
            String imageUrl = imageService.getImageUrl(eventEntryDto.getImage());
            EventEntity eventEntity = EventMapper.mapDtoToEntity(eventEntryDto);
            eventEntity.setImageUrl(imageUrl);

            eventRepository.save(eventEntity);

            log.info("Event added successfully :: {}", eventEntity);
            return EventMapper.mapEntityToDto(eventEntity);
        } catch (InvalidImageException e) {
            log.error("Invalid image: {}", e.getMessage(), e);
            throw e; // Let GlobalExceptionHandler handle it
        } catch (Exception e) {
            log.error("Error while adding event :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add event", e);
        }
    }

    @Override
    public EventResponseDto getEvent(String id) {
        log.info("Fetching movie by ID :: {}", id);
        try {
            return eventRepository.findEventById(id)
                    .map(EventMapper::mapEntityToDto)
                    .orElseThrow(() -> {
                        log.error("Movie not found with ID :: {}", id);
                        return new RuntimeException("Movie not found with ID: " + id);
                    });
        } catch (Exception e) {
            log.error("Error while fetching event by ID :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch event", e);
        }
    }

    @Override
    public List<EventResponseDto> getAllEvent() {
        log.info("Fetching all events");
        try {
            List<EventEntity> eventEntities = eventRepository.findAll();
            eventEntities.sort(Comparator.comparing(EventEntity::getStartDate));
            if (eventEntities.isEmpty()) {
                log.warn("No event found in the database");
            }
            return eventEntities.stream()
                    .map(EventMapper::mapEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching all events :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all events", e);
        }
    }
}