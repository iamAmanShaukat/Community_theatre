package project.community.theatre.service.impl;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.community.theatre.exception.DiscountNotFoundException;
import project.community.theatre.model.DiscountEntity;
import project.community.theatre.repository.DiscountRepository;
import project.community.theatre.service.DiscountService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    public List<DiscountEntity> getAllDiscounts() {
        log.info("Fetching all discounts");
        return discountRepository.findAll();
    }

    @Override
    public DiscountEntity getDiscountByType(String discountType) {
        log.info("Fetching discount for type: {}", discountType);
        return discountRepository.findByDiscountType(discountType)
                .orElseThrow(() -> new DiscountNotFoundException("Discount not found for type: " + discountType));
    }
    @Override
    public DiscountEntity createOrUpdateDiscount(DiscountEntity discount) {
        log.info("Creating or updating discount: {}", discount);
        return discountRepository.save(discount);
    }

    @Override
    @Transactional
    public void deleteDiscount(Long id) {
        if (!discountRepository.existsById(id)) {
            throw new DiscountNotFoundException("Discount not found for ID: " + id);
        }
        discountRepository.deleteById(id);
    }
}