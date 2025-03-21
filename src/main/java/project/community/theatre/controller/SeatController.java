package project.community.theatre.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.constant.AppConstants;
import project.community.theatre.service.impl.SeatServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppConstants.BASE_URL + "/seats")
@CrossOrigin("*")
@Slf4j
public class SeatController {

    @Autowired
    private SeatServiceImpl seatService;

    /**
     * This endpoint verifies and locks the given seats for a specific event and show ID. It takes the event ID,
     * show ID and a list of seat numbers as input and returns a ResponseEntity containing the result of the
     * verification. If the seats are available, it locks the seats and returns a map containing a status flag
     * set to true. If the seats are unavailable, it returns a map containing a status flag set to false and
     * a list of unavailable seats. If an unexpected error occurs, it returns a ResponseEntity with a status of
     * INTERNAL_SERVER_ERROR and a map containing the error message.
     *
     * @param eventId the event ID
     * @param showId the show ID
     * @param seatNumbers the list of seat numbers
     * @return a ResponseEntity containing the result of the verification
     */
    @PostMapping("/verify/{eventId}/{showId}")
    public ResponseEntity<?> verifySeatStatus(@PathVariable String eventId, @PathVariable String showId,
                                              @RequestBody List<String> seatNumbers) {
        log.info("Received request to verify and lock seats for event ID: {}, show ID: {} and seats: {}", eventId, showId, seatNumbers);
        try {
            
            Map<String, Object> result = seatService.processSeatsAvailability(eventId, showId, seatNumbers);

            if (Boolean.FALSE.equals(result.get("status"))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * This endpoint fetches the list of booked seats for a specific event and show ID. It takes the event ID and
     * show ID as input and returns a ResponseEntity containing the list of booked seats. If an unexpected error
     * occurs, it returns a ResponseEntity with a status of INTERNAL_SERVER_ERROR and a map containing the error
     * message.
     *
     * @param eventId the event ID
     * @param showId the show ID
     * @return a ResponseEntity containing the list of booked seats
     */
    @GetMapping("/booked-seats/{eventId}/{showId}")
    public ResponseEntity<List<String>> getAllBookedSeats(@PathVariable String eventId, @PathVariable String showId) {
        log.info("Fetching locked seats for event ID: {} and show ID: {}", eventId, showId);
    
        List<String> lockedSeats = seatService.getAllBookedSeats(eventId, showId);
    
        return ResponseEntity.ok(lockedSeats);
    }
}