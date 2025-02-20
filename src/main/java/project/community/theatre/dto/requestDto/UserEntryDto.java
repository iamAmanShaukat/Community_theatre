package project.community.theatre.dto.requestDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserEntryDto {
    String name;
    String mobNo;
}
