package project.community.theatre.Dto.ResponseDto;

import lombok.Builder;
import lombok.Data;
import project.community.theatre.Dto.TicketDto;

import java.util.List;

@Data
@Builder
public class UserResponseDto {
    int id;
    String name;
    String mobNo;

    // optional
    List<TicketDto> tickets;
}
