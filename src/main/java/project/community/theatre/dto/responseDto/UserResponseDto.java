package project.community.theatre.dto.responseDto;

import lombok.Builder;
import lombok.Data;
import project.community.theatre.dto.TicketDto;

import java.util.List;

@Data
@Builder
public class UserResponseDto {
    String id;
    String name;
    String mobNo;

    // optional
    List<TicketDto> tickets;
}
