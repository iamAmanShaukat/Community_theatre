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
    private String id;
    private String ticketNumber;
    private Double totalPrice;
    private String seatNumbers;
    private String showTime;
    private LocalDateTime bookingTime;
    private String status;

    public TicketResponse(String id, String ticketNumber, Double totalPrice, String seatNumbers, LocalDateTime showTime, String name) {
    }
}
