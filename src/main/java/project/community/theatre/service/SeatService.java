package project.community.theatre.service;

import java.util.List;
import java.util.Map;

/**
 * This interface provides methods for managing seat operations in a community theatre system.
 */
public interface SeatService {

    /**
     * Locks the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be locked.
     */
    void lockSeats(String eventId, String showId, List<String> seatNumbers);

    /**
     * Checks the availability of the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be checked.
     * @return A list of seat numbers that are available.
     */
    List<String> checkSeatsAvailability(String eventId, String showId, List<String> seatNumbers);

    /**
     * Processes the availability of the specified seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param seatNumbers The list of seat numbers to be processed.
     * @return A map containing the following keys:
     *         - "availableSeats": A list of seat numbers that are available.
     *         - "lockedSeats": A list of seat numbers that are locked.
     */
    Map<String, Object> processSeatsAvailability(String eventId, String showId, List<String> seatNumbers);

    /**
     * Locks the specified booked seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @param bookedSeats The list of seat numbers to be locked as booked.
     */
    void lockBookedSeats(String eventId, String showId, List<String> bookedSeats);

    /**
     * Retrieves all booked seats for a given event and show.
     *
     * @param eventId The unique identifier of the event.
     * @param showId The unique identifier of the show.
     * @return A list of seat numbers that are booked.
     */
    List<String> getAllBookedSeats(String eventId, String showId);
}
