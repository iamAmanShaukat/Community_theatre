package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/movie")
@Slf4j
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping(value = "/get-movie")
    public ResponseEntity<EventResponseDto> getMovie(@RequestParam("id") int id) {
        log.info("Received request to fetch movie with ID: {}", id);
        EventResponseDto eventResponseDto = eventService.getEvent(id);
        log.info("Returning movie: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.OK);
    }


    @GetMapping(value = "/get-all-movies")
    public ResponseEntity<List<EventResponseDto>> getAllMovies() {
        log.info("Received request to fetch all movies");
        List<EventResponseDto> movies = eventService.getAllEvent();
        log.info("Returning {} movies", movies.size());
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }

    @PostMapping(value = "/add-movie", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EventResponseDto> addMovie(
            @Valid @RequestPart("movieDetails") EventEntryDto eventEntryDto,
            @RequestPart("image") MultipartFile image) {
        log.info("Received request to add a new movie: {}", eventEntryDto);
        eventEntryDto.setImage(image);
        EventResponseDto eventResponseDto = eventService.addEvent(eventEntryDto);
        log.info("Movie added successfully: {}", eventResponseDto);
        return new ResponseEntity<>(eventResponseDto, HttpStatus.CREATED);
    }
}