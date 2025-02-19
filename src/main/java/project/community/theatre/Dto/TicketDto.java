package project.community.theatre.Dto;

import lombok.Builder;
import lombok.Data;
import project.community.theatre.Dto.ResponseDto.ShowResponseDto;
import project.community.theatre.Dto.ResponseDto.UserResponseDto;

@Data
@Builder
public class TicketDto {
    int id;
    String alotedSeats;
    double amount;
    UserResponseDto user;
    ShowResponseDto show;
}
