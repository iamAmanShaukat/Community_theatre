package project.community.theatre.service;

import project.community.theatre.model.ShowTimeEntity;

import java.util.List;

/**
 * This interface provides methods for managing show times in a community theatre system.
 */
public interface ShowTimeService {

    /**
     * Adds a new showtime to the system.
     *
     * @param showTime The showtime entity to be added.
     * @return The added showtime entity with its unique identifier populated.
     */
    ShowTimeEntity addShowTimes(ShowTimeEntity showTime);

    /**
     * Updates an existing showtime in the system.
     *
     * @param showTime The showtime entity with updated information.
     */
    void updateShowTime(ShowTimeEntity showTime);

    /**
     * Deletes a showtime from the system.
     *
     * @param showTimeId The unique identifier of the showtime to be deleted.
     */
    void deleteShowTime(Long showTimeId);

    /**
     * Retrieves all show times for a specific event.
     *
     * @param eventId The unique identifier of the event.
     * @return A list of showtime entities associated with the given event.
     */
    List<ShowTimeEntity> getAllShowTimes(String eventId);
}

