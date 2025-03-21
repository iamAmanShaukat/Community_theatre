package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.model.BandEntity;

import java.util.List;

public interface BandService {
    /**
     * Retrieves all band entities.
     *
     * @return a list of all band entities.
     */
    List<BandEntity> getAllBands();

    /**
     * Retrieves a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be retrieved. Must not be null or empty.
     * @return the band entity if found, or null if not found.
     */
    BandEntity getBandById(String bandId);

    /**
     * Creates a band entity.
     *
     * @param band the band entity to be created. Must be valid and not null.
     * @return the saved band entity.
     */
    BandEntity createBand(BandEntity band);

    /**
     * Deletes a band entity by its unique identifier.
     *
     * @param bandId the unique identifier of the band to be deleted. Must not be null or empty.
     */
    @Transactional
    void deleteBand(String bandId);
}
