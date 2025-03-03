package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.responseDto.TicketResponse;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.service.TicketService;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
@Slf4j
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/{ticketNumber}")
    public ResponseEntity<TicketResponse> getTicketDetails(@PathVariable String ticketNumber) {
        log.info("Received request to fetch ticket details for ticket number: {}", ticketNumber);

        try {
            TicketResponse ticketResponse = ticketService.getTicketDetails(ticketNumber);
            return ResponseEntity.ok(ticketResponse);
        } catch (ResourceNotFoundException e) {
            log.warn(e.getMessage());
            return ResponseEntity.status(404).body(null); // Return 404 if ticket not found
        }
    }
}