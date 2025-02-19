package project.community.theatre.Dto.EntryRequestDto;

import lombok.Builder;
import lombok.Data;
import project.community.theatre.enums.TheaterType;

@Data
@Builder
public class TheaterEntryDto {
    String name;
    String address;
    String city;
    TheaterType theaterType;
}
