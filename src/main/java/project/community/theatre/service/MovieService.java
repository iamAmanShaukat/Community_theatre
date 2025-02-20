package project.community.theatre.service;


import project.community.theatre.dto.requestDto.MovieEntryDto;
import project.community.theatre.dto.responseDto.MovieResponseDto;

import java.util.List;

public interface MovieService {
    // Add Movie
    MovieResponseDto addMovie(MovieEntryDto movieEntryDto);
    
    // Get Movie
    MovieResponseDto getMovie(int id);

    List<MovieResponseDto> getAllMovies();

}
