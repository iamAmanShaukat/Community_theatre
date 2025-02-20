package project.community.theatre.service;


import project.community.theatre.dto.requestDto.TheaterEntryDto;
import project.community.theatre.dto.responseDto.TheaterResponseDto;

public interface TheaterService {
    // Add Theater
    TheaterResponseDto addTheater(TheaterEntryDto theaterEntryDto);

    // Get Theater
    TheaterResponseDto getTheater(int id);
}
