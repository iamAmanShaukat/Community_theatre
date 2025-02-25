package project.community.theatre.service;

import org.springframework.transaction.annotation.Transactional;
import project.community.theatre.model.BandEntity;

import java.util.List;

public interface BandService {
    List<BandEntity> getAllBands();

    BandEntity getBandById(String bandId);

    BandEntity createBand(BandEntity band);

    @Transactional
    void deleteBand(String bandId);
}
