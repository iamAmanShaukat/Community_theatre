package project.community.theatre.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.constant.AppConstants;
import project.community.theatre.service.SeatServiceImpl;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppConstants.BASE_URL+"/seats")
@Slf4j
public class SeatController {

    @Autowired
    private SeatServiceImpl seatServiceImpl;

    @PostMapping("/verify/{showId}")
    public ResponseEntity<?> checkAndLockSeats(@PathVariable String showId,
                                               @RequestBody List<String> seatNumbers) {
        log.info("Received request to verify and lock seats for show ID: {} and seats: {}", showId, seatNumbers);
        try {
            Map<String, Object> result = seatServiceImpl.processSeatsAvailability(showId, seatNumbers);
    
            if (Boolean.FALSE.equals(result.get("status"))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(result);
            }
            // All seats are locked successfully
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}