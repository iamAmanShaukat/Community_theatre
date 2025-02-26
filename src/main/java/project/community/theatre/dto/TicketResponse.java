package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private Long id; // Ticket ID
    private String ticketNumber; // Unique ticket number
    private Double totalPrice; // Total price of the ticket
    private List<String> seatNumbers; // Comma-separated list of seat numbers
    private LocalDateTime showTime; // Show time
    private LocalDateTime bookingTime; // Booking time
    private String status; // Ticket status (e.g., BOOKED)

    public TicketResponse(String id, String ticketNumber, Double totalPrice, String seatNumbers, LocalDateTime showTime, String name) {


    }
}
