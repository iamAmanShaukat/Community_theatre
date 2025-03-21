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

    /**
     * Retrieves all band entities from the database.
     *
     * @return a ResponseEntity containing a list of band entities, or an appropriate error response if the list is empty.
     */
    @GetMapping(value = "/all")
    public ResponseEntity<List<BandEntity>> getAllBands() {
        log.info("Received request to get all bands");
        List<BandEntity> bands = bandService.getAllBands();
        return ResponseEntity.ok(bands);
    }

    /**
     * Creates or updates a band entity.
     *
     * @param band the band entity to be created or updated. Must be valid and not null.
     * @return a ResponseEntity containing the saved band entity.
     */
    @PostMapping(value = "/create", consumes = "application/json")
    public ResponseEntity<BandEntity> createOrUpdateBand(@Valid @RequestBody BandEntity band) {
        log.info("Received request to create bands");
        BandEntity savedBand = bandService.createBand(band);
        return ResponseEntity.ok(savedBand);
    }

    /**
     * Retrieves a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be retrieved. Must not be null or empty.
     * @return a ResponseEntity containing the band entity if found, or an appropriate error response if not found.
     */
    @GetMapping("/get/{bandId}")
    public ResponseEntity<BandEntity> getBandById(@PathVariable String bandId) {
        log.info("Fetching band for ID: {}", bandId);
        BandEntity band = bandService.getBandById(bandId);
        return ResponseEntity.ok(band);
    }

    /**
     * Deletes a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be deleted. Must not be null or empty.
     * @return a ResponseEntity with an empty body indicating success, or an appropriate error response if the entity
     * could not be found.
     */
    @DeleteMapping("/{bandId}")
    public ResponseEntity<Void> deleteBand(@PathVariable String bandId) {
        log.info("Received request to delete band for ID: {}", bandId);
        bandService.deleteBand(bandId);
        return ResponseEntity.ok().build();
    }
}