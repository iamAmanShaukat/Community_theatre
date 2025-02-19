package project.community.theatre.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.Dto.EntryRequestDto.ShowEntryDto;
import project.community.theatre.Dto.ResponseDto.ShowResponseDto;
import project.community.theatre.Service.Impl.ShowServiceImpl;

@RestController
@RequestMapping("/show")
public class ShowController {
    @Autowired
    ShowServiceImpl showService;

    @GetMapping("/get-show")
    public ResponseEntity<ShowResponseDto> getShow(@RequestParam("id") int id) {
        return new ResponseEntity<>(showService.getShow(id), HttpStatus.FOUND);
    }

    @PostMapping("/add-show")
    public ResponseEntity<ShowResponseDto> addShow(@RequestBody ShowEntryDto showEntryDto) {
        return new ResponseEntity<>(showService.addShow(showEntryDto), HttpStatus.CREATED);
    }
}
