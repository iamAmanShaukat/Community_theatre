package project.community.theatre.mapper;

import project.community.theatre.dto.requestDto.MovieEntryDto;
import project.community.theatre.dto.responseDto.MovieResponseDto;
import project.community.theatre.model.MovieEntity;

import java.time.LocalDate;

public class MovieConverter {

    public static MovieEntity mapDtoToEntity(MovieEntryDto movieEntryDto) {
        if (movieEntryDto == null) {
            throw new IllegalArgumentException("MovieEntryDto cannot be null");
        }

        // Parse startDate and endDate into LocalDate (assuming format "yyyy-MM-dd")
        LocalDate startDate = LocalDate.parse(movieEntryDto.getStartDate());
        LocalDate endDate = LocalDate.parse(movieEntryDto.getEndDate());

        return MovieEntity.builder()
                .movieId(movieEntryDto.getMovieId())
                .name(movieEntryDto.getName())
                .genre(movieEntryDto.getGenre())
                .startDate(startDate)
                .endDate(endDate)
                .duration(movieEntryDto.getDuration())
                .description(movieEntryDto.getDescription())
                .producer(movieEntryDto.getProducer())
                .director(movieEntryDto.getDirector())
                .build();
    }

    public static MovieResponseDto mapEntityToDto(MovieEntity movieEntity) {
        if (movieEntity == null) {
            throw new IllegalArgumentException("MovieEntity cannot be null");
        }
        return MovieResponseDto.builder()
                .movieId(movieEntity.getMovieId())
                .name(movieEntity.getName())
                .genre(movieEntity.getGenre())
                .startDate(movieEntity.getStartDate())
                .endDate(movieEntity.getEndDate())
                .duration(movieEntity.getDuration())
                .description(movieEntity.getDescription())
                .producer(movieEntity.getProducer())
                .director(movieEntity.getDirector())
                .imageUrl(movieEntity.getImageUrl())
                .build();
    }
}