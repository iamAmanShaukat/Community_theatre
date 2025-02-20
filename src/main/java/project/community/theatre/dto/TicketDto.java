package project.community.theatre.dto;

import lombok.Builder;
import lombok.Data;
import project.community.theatre.dto.responseDto.UserResponseDto;

@Data
@Builder
public class TicketDto {
    int id;
    String alotedSeats;
    double amount;
    UserResponseDto user;
}
