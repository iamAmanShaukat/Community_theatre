package project.community.theatre.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.community.theatre.dto.AddressDto;
import project.community.theatre.dto.PaymentDetailsDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private PaymentDetailsDto paymentDetails;
    private AddressDto address;
    private String name;
    private Double payableAmount;
    private String userId;
    private String email;
    private String eventId;
    private String showId;
    private String showTime;
    private List<String> seatNumbers;
}