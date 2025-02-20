package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.dto.requestDto.MovieEntryDto;
import project.community.theatre.dto.responseDto.MovieResponseDto;
import project.community.theatre.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movie")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping(value = "/get-movie")
    public ResponseEntity<MovieResponseDto> getMovie(@RequestParam("id") int id) {
        log.info("Received request to fetch movie with ID: {}", id);
        MovieResponseDto movieResponseDto = movieService.getMovie(id);
        log.info("Returning movie: {}", movieResponseDto);
        return new ResponseEntity<>(movieResponseDto, HttpStatus.OK);
    }


    @GetMapping(value = "/get-all-movies")
    public ResponseEntity<List<MovieResponseDto>> getAllMovies() {
        log.info("Received request to fetch all movies");
        List<MovieResponseDto> movies = movieService.getAllMovies();
        log.info("Returning {} movies", movies.size());
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping(value = "/add-movie", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<MovieResponseDto> addMovie(
            @Valid @RequestPart("movieDetails") MovieEntryDto movieEntryDto,
            @RequestPart("image") MultipartFile image) {
        log.info("Received request to add a new movie: {}", movieEntryDto);
        movieEntryDto.setImage(image);
        MovieResponseDto movieResponseDto = movieService.addMovie(movieEntryDto);
        log.info("Movie added successfully: {}", movieResponseDto);
        return new ResponseEntity<>(movieResponseDto, HttpStatus.CREATED);
    }
}