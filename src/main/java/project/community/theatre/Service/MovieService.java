package project.community.theatre.Service;


import project.community.theatre.Dto.EntryRequestDto.MovieEntryDto;
import project.community.theatre.Dto.ResponseDto.MovieResponseDto;

public interface MovieService {
    // Add Movie
    MovieResponseDto addMovie(MovieEntryDto movieEntryDto);
    
    // Get Movie
    MovieResponseDto getMovie(int id);

}
