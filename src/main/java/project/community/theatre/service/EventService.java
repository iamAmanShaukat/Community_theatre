package project.community.theatre.service;


import project.community.theatre.dto.responseDto.ShowTimeResponseDto;
import project.community.theatre.dto.requestDto.AddShowTimesRequestDto;
import project.community.theatre.dto.requestDto.DeleteShowTimeRequestDto;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;

import java.util.List;


/**
 * This interface defines the methods for managing events and their show times.
 */
public interface EventService {

    /**
     * Adds a new event to the system.
     *
     * @param eventEntryDto The details of the event to be added.
     * @return The response containing the details of the newly added event.
     */
    EventResponseDto addEvent(EventEntryDto eventEntryDto);

    /**
     * Retrieves the details of a specific event.
     *
     * @param id The unique identifier of the event.
     * @return The response containing the details of the requested event.
     */
    EventResponseDto getEvent(String id);

    /**
     * Retrieves the details of all events in the system.
     *
     * @return A list of responses containing the details of all events.
     */
    List<EventResponseDto> getAllEvent();

    /**
     * Deletes an event from the system.
     *
     * @param eventId The unique identifier of the event to be deleted.
     */
    void deleteEvent(String eventId);

    /**
     * Adds show times for a specific event.
     *
     * @param request The request containing the details of the show times to be added.
     */
    void addShowTimes(AddShowTimesRequestDto request);

    /**
     * Retrieves the show times for a specific event.
     *
     * @param eventId The unique identifier of the event.
     * @return A list of responses containing the details of the show times for the event.
     */
    List<ShowTimeResponseDto> getShowTimesForEvent(String eventId);

    /**
     * Deletes a specific showtime from an event.
     *
     * @param request The request containing the details of the show time to be deleted.
     */
    void deleteShowTime(DeleteShowTimeRequestDto request);
}
