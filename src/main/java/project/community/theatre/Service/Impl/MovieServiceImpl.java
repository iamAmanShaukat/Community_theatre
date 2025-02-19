package project.community.theatre.Service.Impl;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.community.theatre.Converter.MovieConverter;
import project.community.theatre.Dto.EntryRequestDto.MovieEntryDto;
import project.community.theatre.Dto.ResponseDto.MovieResponseDto;
import project.community.theatre.Model.MovieEntity;
import project.community.theatre.Repository.MovieRepository;
import project.community.theatre.Service.MovieService;

@Service
@Component
@Slf4j
public class MovieServiceImpl implements MovieService {
    @Autowired
    MovieRepository movieRepository;

    @Override
    public MovieResponseDto addMovie(MovieEntryDto movieEntryDto) {

        log.info("Adding the Movie", movieEntryDto);

        MovieEntity movieEntity = MovieConverter.convertDtoToEntity(movieEntryDto);
        movieRepository.save(movieEntity);

        return MovieConverter.convertEntityToDto(movieEntity);
    }

    @Override
    public MovieResponseDto getMovie(int id) {
        MovieEntity movieEntity = movieRepository.findById(id).get();
        MovieResponseDto movieResponseDto = MovieConverter.convertEntityToDto(movieEntity);
        return movieResponseDto;
    }
    
}
