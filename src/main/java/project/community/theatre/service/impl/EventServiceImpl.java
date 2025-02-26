package project.community.theatre.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.exception.InvalidImageException;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.mapper.EventMapper;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ShowTimeEntity;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.repository.EventRepository;
import project.community.theatre.repository.ShowTimeRepository;
import project.community.theatre.dto.ShowTimeResponseDto;
import project.community.theatre.repository.TicketRepository;
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

    @Autowired
    private ShowTimeRepository showTimeRepository;

    @Autowired
    private TicketRepository ticketRepository;

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

    @Override
    public void deleteEvent(String eventId) {
        log.info("Deleting event with ID: {}", eventId);

        // Fetch the event
        EventEntity event = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Delete associated tickets
        List<TicketEntity> tickets = ticketRepository.findEventById(eventId);
        ticketRepository.deleteAll(tickets);

        // Delete associated show times
        List<ShowTimeEntity> showTimes = showTimeRepository.findByEvent(event);
        showTimeRepository.deleteAll(showTimes);

        // Delete the event
        eventRepository.delete(event);

        log.info("Event and all associated data deleted successfully for ID: {}", eventId);
    }

    @Override
    public void addShowTimes(AddShowTimesRequestDto request) {
        log.info("Adding show times for event ID: {}", request.getEventId());

        EventEntity eventEntity = eventRepository.findEventById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

        List<ShowTimeEntity> showTimeEntities = request.getShowTimes().stream()
                .map(showTime -> ShowTimeEntity.builder()
                        .event(eventEntity)
                        .showTime(showTime)
                        .build())
                .toList();

        if (eventEntity.getShowTimes() == null) {
            eventEntity.setShowTimes(showTimeEntities);
        } else {
            eventEntity.getShowTimes().addAll(showTimeEntities);
        }

        eventRepository.save(eventEntity);
        log.info("Show times added successfully for event ID: {}", request.getEventId());
    }

    @Override
    public List<ShowTimeResponseDto> getShowTimesForEvent(String eventId) {
        log.info("Fetching show times for event ID: {}", eventId);

        EventEntity eventEntity = eventRepository.findEventById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + eventId));

        // Extract the show times from the event's showTimes field
        return eventEntity.getShowTimes().stream()
                .map(showTimeEntity -> ShowTimeResponseDto.builder()
                        .id(showTimeEntity.getId())
                        .showTime(showTimeEntity.getShowTime())
                        .build())
                .toList();
    }

    @Override
    public void deleteShowTime(DeleteShowTimeRequestDto request) {
        log.info("Deleting show time {} for event ID: {}", request.getShowTime(), request.getEventId());

        EventEntity eventEntity = eventRepository.findEventById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with ID: " + request.getEventId()));

        if (eventEntity.getShowTimes() != null && eventEntity.getShowTimes().remove(request.getShowTime())) {
            eventRepository.save(eventEntity);
            log.info("Show time deleted successfully: {}", request.getShowTime());
        } else {
            throw new ResourceNotFoundException("Show time not found: " + request.getShowTime());
        }
    }

}