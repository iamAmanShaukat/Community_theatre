package project.community.theatre.Service;


import project.community.theatre.Dto.EntryRequestDto.ShowEntryDto;
import project.community.theatre.Dto.ResponseDto.ShowResponseDto;

public interface ShowService {
    // Add Show
    ShowResponseDto addShow(ShowEntryDto showEntryDto);

    // Get Show
    ShowResponseDto getShow(int id);
}
