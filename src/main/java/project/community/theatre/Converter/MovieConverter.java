package project.community.theatre.Converter;


import project.community.theatre.Dto.EntryRequestDto.MovieEntryDto;
import project.community.theatre.Dto.ResponseDto.MovieResponseDto;
import project.community.theatre.Model.MovieEntity;

public class MovieConverter {
    public static MovieEntity convertDtoToEntity(MovieEntryDto movieEntryDto) {
        return MovieEntity.builder()
                .name(movieEntryDto.getName())
                .releaseDate(movieEntryDto.getReleaseDate()).build();
    }
    public static MovieResponseDto convertEntityToDto(MovieEntity movieEntity) {
        return MovieResponseDto.builder()
                .id(movieEntity.getId())
                .name(movieEntity.getName())
                .releaseDate(movieEntity.getReleaseDate()).build();
    }
}
