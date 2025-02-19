package project.community.theatre.Dto.EntryRequestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntryDto {
    String name;
    String mobNo;
}
