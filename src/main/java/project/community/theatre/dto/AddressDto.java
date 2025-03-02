package project.community.theatre.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDto {
    private String street;
    private String apartment;
    private String city;
    private String country;
    private String state;
    private String zipCode;
}
