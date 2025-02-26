package project.community.theatre.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    private Double amount;
    private String userId;
    private String eventId;
    private String showId;
    private String showTime;
    private List<String> seatNumbers;
}