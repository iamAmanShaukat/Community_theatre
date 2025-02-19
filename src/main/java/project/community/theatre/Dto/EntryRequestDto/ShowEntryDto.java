package project.community.theatre.Dto.EntryRequestDto;

import lombok.Builder;
import lombok.Data;
import project.community.theatre.Dto.ResponseDto.MovieResponseDto;
import project.community.theatre.Dto.ResponseDto.TheaterResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class ShowEntryDto {
    LocalDate showDate;

    LocalTime showTime;

    MovieResponseDto movieResponseDto;

    TheaterResponseDto theaterResponseDto;
}
