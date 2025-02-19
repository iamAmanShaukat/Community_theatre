package project.community.theatre.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.community.theatre.Converter.MovieConverter;
import project.community.theatre.Dto.EntryRequestDto.MovieEntryDto;
import project.community.theatre.Dto.ResponseDto.MovieResponseDto;
import project.community.theatre.Model.MovieEntity;
import project.community.theatre.Repository.MovieRepository;
import project.community.theatre.Service.MovieService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Override
    public MovieResponseDto addMovie(MovieEntryDto movieEntryDto) {
        log.info("Adding a new movie :: {}", movieEntryDto);
        try {
            MovieEntity movieEntity = MovieConverter.convertDtoToEntity(movieEntryDto);
            movieRepository.save(movieEntity);
            log.info("Movie added successfully :: {}", movieEntity);
            return MovieConverter.convertEntityToDto(movieEntity);
        } catch (Exception e) {
            log.error("Error while adding movie :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to add movie", e);
        }
    }

    @Override
    public MovieResponseDto getMovie(int id) {
        log.info("Fetching movie by ID :: {}", id);
        try {
            return movieRepository.findById(id)
                    .map(MovieConverter::convertEntityToDto)
                    .orElseThrow(() -> {
                        log.error("Movie not found with ID :: {}", id);
                        return new RuntimeException("Movie not found with ID: " + id);
                    });
        } catch (Exception e) {
            log.error("Error while fetching movie by ID :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch movie", e);
        }
    }

    @Override
    public List<MovieResponseDto> getAllMovies() {
        log.info("Fetching all movies");
        try {
            List<MovieEntity> movieEntities = movieRepository.findAll();
            if (movieEntities.isEmpty()) {
                log.warn("No movies found in the database");
            }
            return movieEntities.stream()
                    .map(MovieConverter::convertEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error while fetching all movies :: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch all movies", e);
        }
    }
}