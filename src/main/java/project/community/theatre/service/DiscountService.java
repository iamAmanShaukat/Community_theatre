package project.community.theatre.service;

import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.model.DiscountEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * This interface provides methods for managing discounts in the community theatre system.
 */
public interface DiscountService {
    /**
     * Calculates and returns the discount amount based on the given discount request.
     *
     * @return The discount response containing the calculated discount amount.
     */
    DiscountResponse calculateDiscount(DiscountRequest request);

    /**
     * Retrieves all discount entities from the database.
     *
     * @return A list of all discount entities.
     */
    List<DiscountEntity> getAllDiscounts();

    /**
     * Retrieves a discount entity based on the given discount type.
     *
     * @param discountType The type of discount to retrieve.
     * @return The discount entity with the specified discount type, or null if not found.
     */
    DiscountEntity getDiscountByType(String discountType);

    /**
     * Creates or updates a discount entity in the database.
     *
     * @param discount The discount entity to be created or updated.
     * @return The saved discount entity.
     */
    DiscountEntity createOrUpdateDiscount(DiscountEntity discount);

    /**
     * Deletes a discount entity from the database based on the given ID.
     * This method is annotated with {@link Transactional} to ensure atomicity.
     *
     * @param id The ID of the discount entity to be deleted.
     */
    @Transactional
    void deleteDiscount(String id);
}
