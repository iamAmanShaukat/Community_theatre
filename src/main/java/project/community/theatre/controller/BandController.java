package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.model.BandEntity;
import project.community.theatre.service.BandService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bands")
@RequiredArgsConstructor
@Slf4j
public class BandController {

    private final BandService bandService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<BandEntity>> getAllBands() {
        log.info("Received request to get all bands");
        List<BandEntity> bands = bandService.getAllBands();
        return ResponseEntity.ok(bands);
    }

    @PostMapping(value = "/create", consumes = "application/json"  )
    public ResponseEntity<BandEntity> createOrUpdateBand(@Valid@RequestBody BandEntity band) {
        log.info("Received request to create bands");
        BandEntity savedBand = bandService.createBand(band);
        return ResponseEntity.ok(savedBand);
    }

    @GetMapping("/get/{bandId}")
    public ResponseEntity<BandEntity> getBandById(@PathVariable String bandId) {
        log.info("Fetching band for ID: {}", bandId);
        BandEntity band = bandService.getBandById(bandId);
        return ResponseEntity.ok(band);
    }

    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> deleteBand(@PathVariable String bandId) {
        log.info("Received request to delete band for ID: {}", bandId);
        bandService.deleteBand(bandId);
        return ResponseEntity.ok().build();
    }
}