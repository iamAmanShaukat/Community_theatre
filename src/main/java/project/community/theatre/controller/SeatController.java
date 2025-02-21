package project.community.theatre.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.constant.AppConstants;
import project.community.theatre.service.SeatService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(AppConstants.BASE_URL+"/seats")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @PostMapping("/lock/{showId}")
    public ResponseEntity<Map<String, String>> lockSeats(@PathVariable String showId,
                                                         @RequestBody List<String> seatNumbers) {
        try {
            Map<String, String> results = seatService.lockSeats(showId, seatNumbers);
            return ResponseEntity.ok(results);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/check/{showId}")
    public ResponseEntity<List<String>> checkSeatsAvailability(@PathVariable String showId,
                                                               @RequestBody List<String> seatNumbers) {
        try {
            List<String> availableSeats = seatService.checkSeatsAvailability(showId, seatNumbers);
            return ResponseEntity.ok(availableSeats);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(List.of("error", e.getMessage()));
        }
    }
}