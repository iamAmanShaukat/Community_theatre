package project.community.theatre.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.BandEntity;

import java.util.List;
import java.util.Optional;

public interface BandRepository extends JpaRepository<BandEntity, String> {
    @NotNull List<BandEntity> findAll();

    Optional<BandEntity> findByBandId(String bandId);

    void deleteByBandId(String bandId);

    boolean existsByBandId(String discountType);
}