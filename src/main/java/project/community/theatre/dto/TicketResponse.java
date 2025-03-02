package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponse {
    private String ticketNumber;
    private Double totalPrice;
    private String seatNumbers;
    private String showTime;
    private String eventName;
    private LocalDateTime bookingTime;
    private String status;
}
