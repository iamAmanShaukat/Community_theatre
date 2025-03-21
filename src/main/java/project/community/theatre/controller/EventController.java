package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.dto.responseDto.ShowTimeResponseDto;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.service.EventService;

import java.util.List;

/**
 * This class is a controller for handling event-related operations.
 * It provides RESTful endpoints for fetching, adding, and deleting events, as well as managing their show times.
 *
 */
@RestController
@RequestMapping("/event")
@CrossOrigin("*")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    /**
     * This method fetches an event by its ID.
     *
     * @param id The unique identifier of the event to fetch.
     * @return A ResponseEntity containing the fetched event wrapped in an EventResponseDto object.
     */
    @GetMapping(value = "/get-event")
    public ResponseEntity<EventResponseDto> getEvent(@RequestParam("id") String id) {
        log.info("Received request to fetch event with ID: {}", id);

        EventResponseDto eventResponseDto = eventService.getEvent(id);

        log.info("Returning event: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }

    /**
     * This method fetches all events.
     *
     * @return A ResponseEntity containing a list of all events wrapped in an EventResponseDto object.
     */
    @GetMapping(value = "/get-all-events")
    public ResponseEntity<List<EventResponseDto>> getAllEvents() {
        log.info("Received request to fetch all events");

        List<EventResponseDto> events = eventService.getAllEvent();

        log.info("Returning {} events", events.size());
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    /**
     * This method adds a new event.
     *
     * @param eventEntryDto The details of the new event to be added.
     * @param image         The image file associated with the new event.
     * @return A ResponseEntity containing the newly added event wrapped in an EventResponseDto object.
     */
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

    /**
     * This method deletes an event by its ID.
     *
     * @param eventId The unique identifier of the event to be deleted.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable String eventId) {
        log.info("Received request to delete event with ID: {}", eventId);

        eventService.deleteEvent(eventId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method adds show times for a specific event.
     *
     * @param request The details of the new show time to be added.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @PostMapping("/add-show-times")
    public ResponseEntity<Void> addShowTimes(@RequestBody AddShowTimesRequestDto request) {
        log.info("Received request to add show times for event ID: {}", request.getEventId());

        eventService.addShowTimes(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * This method fetches show times for a specific event.
     *
     * @param eventId The unique identifier of the event to fetch its show times.
     * @return A ResponseEntity containing a list of show times wrapped in a ShowTimeResponseDto object.
     */
    @GetMapping("/{eventId}/get-show-times")
    public ResponseEntity<List<ShowTimeResponseDto>> getShowTimesForEvent(@PathVariable String eventId) {
        log.info("Received request to fetch show times for event ID: {}", eventId);

        List<ShowTimeResponseDto> showTimes = eventService.getShowTimesForEvent(eventId);

        return new ResponseEntity<>(showTimes, HttpStatus.OK);
    }

    /**
     * This method deletes a specific showtime for a specific event.
     *
     * @param request The details of the showtime to be deleted.
     * @return A ResponseEntity with an empty body and an OK status code.
     */
    @DeleteMapping("/delete-show-time")
    public ResponseEntity<Void> deleteShowTime(@RequestBody DeleteShowTimeRequestDto request) {
        log.info("Received request to delete show time {} for event ID: {}", request.getShowTime(), request.getEventId());

        eventService.deleteShowTime(request);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}