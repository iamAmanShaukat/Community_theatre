package project.community.theatre.service;

import java.util.List;
import java.util.Map;

public interface SeatService {
    void lockSeats(String showId, List<String> seatNumbers);

    List<String> checkSeatsAvailability(String showId, List<String> seatNumbers);

    Map<String, Object> processSeatsAvailability(String showId, List<String> seatNumbers);
}
