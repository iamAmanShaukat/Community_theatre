package project.community.theatre.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.DiscountEntity;

import java.util.Optional;

public interface DiscountRepository extends JpaRepository<DiscountEntity, Long> {
    Optional<DiscountEntity> findByDiscountType(String discountType); // Return Optional

    boolean existsById(@NotNull Long id);
    void deleteById(@NotNull Long id);
}