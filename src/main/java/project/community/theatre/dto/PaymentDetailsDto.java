package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetailsDto {
    private String cardNumber;
    private String cardHolderName;
    private String expiryDate;
    private String cvv;

}


