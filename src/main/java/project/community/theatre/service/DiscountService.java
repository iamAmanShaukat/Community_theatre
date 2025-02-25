package project.community.theatre.service;

import project.community.theatre.model.DiscountEntity;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DiscountService {
    List<DiscountEntity> getAllDiscounts();

    DiscountEntity getDiscountByType(String discountType);

    DiscountEntity createOrUpdateDiscount(DiscountEntity discount);

    @Transactional
    void deleteDiscount(Long id);
}
