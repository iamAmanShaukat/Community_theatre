package project.community.theatre.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.exception.BandNotFoundException;
import project.community.theatre.model.BandEntity;
import project.community.theatre.repository.BandRepository;
import project.community.theatre.service.BandService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BandServiceImpl implements BandService {

    private final BandRepository bandRepository;

    @Override
    public List<BandEntity> getAllBands() {
        log.info("Fetching all bands");
        return bandRepository.findAll();
    }

    @Override
    public BandEntity getBandById(String bandId) {
        log.info("Fetching band for ID: {}", bandId);
        return bandRepository.findByBandId(bandId)
                .orElseThrow(() -> new BandNotFoundException("Band not found for ID: " + bandId));
    }

    @Override
    public BandEntity createBand(BandEntity band) {
        log.info("Creating bands: {}", band);
        return bandRepository.save(band);
    }

    @Override
    @Transactional
    public void deleteBand(String bandId) {
        if (!bandRepository.existsByBandId(bandId)) {
            throw new BandNotFoundException("Band not found for ID: " + bandId);
        }
        bandRepository.deleteByBandId(bandId);
    }
}