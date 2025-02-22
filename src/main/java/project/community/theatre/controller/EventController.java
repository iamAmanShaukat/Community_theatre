package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.dto.ShowTimeResponseDto;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/event")
@CrossOrigin("*")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/get-event")
    public ResponseEntity<EventResponseDto> getEvent(@RequestParam("id") String id) {
        log.info("Received request to fetch event with ID: {}", id);

        EventResponseDto eventResponseDto = eventService.getEvent(id);

        log.info("Returning event: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }


    @GetMapping(value = "/get-all-events")
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        log.info("Received request to fetch all events");

        List<EventResponseDto> events = eventService.getAllEvent();

        log.info("Returning {} events", events.size());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }


    @PostMapping(value = "/add-event", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDto> addEvent(
            @Valid @RequestPart("eventDetails") EventEntryDto eventEntryDto,
            @RequestPart("image") MultipartFile image) {
        log.info("Received request to add a new event: {}", eventEntryDto);
        eventEntryDto.setImage(image);

        EventResponseDto eventResponseDto = eventService.addEvent(eventEntryDto);

        log.info("Event added successfully: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        log.info("Received request to delete event with ID: {}", eventId);

        eventService.deleteEvent(eventId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/add-show-times")
    public ResponseEntity<Void> addShowTimes(@RequestBody AddShowTimesRequestDto request) {
        log.info("Received request to add show times for event ID: {}", request.getEventId());

        eventService.addShowTimes(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{eventId}/get-show-times")
    public ResponseEntity<List<ShowTimeResponseDto>> getShowTimesForEvent(@PathVariable String eventId) {
        log.info("Received request to fetch show times for event ID: {}", eventId);

        // Fetch the show times from the service layer
        List<ShowTimeResponseDto> showTimes = eventService.getShowTimesForEvent(eventId);

        // Return the list of show times
        return new ResponseEntity<>(showTimes, HttpStatus.OK);
    }

    @DeleteMapping("/delete-show-time")
    public ResponseEntity<Void> deleteShowTime(@RequestBody DeleteShowTimeRequestDto request) {
        log.info("Received request to delete show time {} for event ID: {}", request.getShowTime(), request.getEventId());

        eventService.deleteShowTime(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}