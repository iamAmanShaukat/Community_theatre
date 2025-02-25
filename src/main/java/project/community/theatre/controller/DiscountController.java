package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.model.DiscountEntity;
import project.community.theatre.service.DiscountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping(value ="/all-discounts")
    public ResponseEntity<List<DiscountEntity>> getAllDiscounts() {
        log.info("Received request to get all discounts");
        List<DiscountEntity> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    @PostMapping(value = "/create-discount" , consumes = "application/json"  )
    public ResponseEntity<DiscountEntity> createOrUpdateDiscount(@Valid @RequestBody DiscountEntity discount) {
        log.info("Received request to create a discount");
        DiscountEntity savedDiscount = discountService.createOrUpdateDiscount(discount);
        return ResponseEntity.ok(savedDiscount);
    }

    @GetMapping("/type/{discountType}")
    public ResponseEntity<DiscountEntity> getDiscountByType(@PathVariable String discountType) {
        log.info("Fetching discount for type: {}", discountType);
        DiscountEntity discount = discountService.getDiscountByType(discountType);
        return ResponseEntity.ok(discount);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable Long id) {
        log.info("Received request to delete discount for id: {}", id);
        discountService.deleteDiscount(id);
        return ResponseEntity.ok().build();
    }
}