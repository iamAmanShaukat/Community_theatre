package project.community.theatre.Service;


import project.community.theatre.Dto.EntryRequestDto.TheaterEntryDto;
import project.community.theatre.Dto.ResponseDto.TheaterResponseDto;

public interface TheaterService {
    // Add Theater
    TheaterResponseDto addTheater(TheaterEntryDto theaterEntryDto);

    // Get Theater
    TheaterResponseDto getTheater(int id);
}
