package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountResponse {
    private double child;
    private double pensioner;
    private double lastHour;
    private double socialClub;
    private double weekday;
    private double finalPrice;
    private double reduction;
}