package project.community.theatre.dto;

import lombok.Builder;
import lombok.Data;
import project.community.theatre.dto.responseDto.UserResponseDto;

@Data
@Builder
public class TicketDto {
    int id;
    String allottedSeats;
    double amount;
    UserResponseDto user;
}
