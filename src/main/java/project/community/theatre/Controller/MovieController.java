package project.community.theatre.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.Dto.EntryRequestDto.MovieEntryDto;
import project.community.theatre.Dto.ResponseDto.MovieResponseDto;
import project.community.theatre.Service.Impl.MovieServiceImpl;


@RestController
@RequestMapping("/movie")
public class MovieController {
    @Autowired
    MovieServiceImpl movieSerivice;

    @GetMapping(value="/get-movie")
    public ResponseEntity<MovieResponseDto> getMovie(@RequestParam("id") int id) {
        return new ResponseEntity<>(movieSerivice.getMovie(id), HttpStatus.FOUND);
    }
    
    @PostMapping("/add-movie")
    public ResponseEntity<MovieResponseDto> addMovie(@RequestBody MovieEntryDto movieEntryDto) {
        return new ResponseEntity<>(movieSerivice.addMovie(movieEntryDto), HttpStatus.CREATED);
    }
}
