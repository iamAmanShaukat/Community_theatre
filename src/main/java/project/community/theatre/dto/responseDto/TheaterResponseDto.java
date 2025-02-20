package project.community.theatre.dto.responseDto;

import lombok.Builder;
import lombok.Data;
import project.community.theatre.enums.TheaterType;

@Data
@Builder
public class TheaterResponseDto {
    int id;
    String name;
    String address;
    String city;
    TheaterType theaterType;
}
