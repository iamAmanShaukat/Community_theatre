package project.community.theatre.service;

import java.util.List;
import java.util.Map;

public interface SeatService {
    void lockSeats(String eventId, String showId, List<String> seatNumbers);

    List<String> checkSeatsAvailability(String eventId, String showId, List<String> seatNumbers);

    Map<String, Object> processSeatsAvailability(String eventId, String showId, List<String> seatNumbers);

    void lockBookedSeats(String eventId, String showId, List<String> bookedSeats);

    List<String> getAllBookedSeats(String eventId, String showId);
}
