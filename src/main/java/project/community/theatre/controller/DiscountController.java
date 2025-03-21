package project.community.theatre.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.DiscountRequest;
import project.community.theatre.dto.responseDto.DiscountResponse;
import project.community.theatre.model.DiscountEntity;
import project.community.theatre.service.DiscountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/discounts")
@RequiredArgsConstructor
@Slf4j
public class DiscountController {

    private final DiscountService discountService;

    /**
     * Calculates the discount based on the provided request details.
     *
     * @param request the DiscountRequest object containing the necessary
     *                information to calculate the discount.
     * @return a ResponseEntity containing a DiscountResponse object with
     *         the calculated discount details, wrapped in an HTTP status of OK.
     */
    @PostMapping("/calculate")
    public ResponseEntity<DiscountResponse> calculateDiscount(@RequestBody DiscountRequest request) {
        DiscountResponse response = discountService.calculateDiscount(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves a list of all discounts.
     *
     * @return a ResponseEntity containing the list of DiscountEntity objects,
     *         wrapped in an HTTP status of OK.
     */
    @GetMapping(value ="/all-discounts")
    public ResponseEntity<List<DiscountEntity>> getAllDiscounts() {
        log.info("Received request to get all discounts");
        List<DiscountEntity> discounts = discountService.getAllDiscounts();
        return ResponseEntity.ok(discounts);
    }

    /**
     * Creates or updates a discount entity.
     *
     * @param discount the DiscountEntity to be created or updated. Must be valid and not null.
     * @return a ResponseEntity containing the saved DiscountEntity, wrapped in an HTTP status of OK.
     */
    @PostMapping(value = "/create-discount" , consumes = "application/json"  )
    public ResponseEntity<DiscountEntity> createOrUpdateDiscount(@Valid @RequestBody DiscountEntity discount) {
        log.info("Received request to create a discount");
        DiscountEntity savedDiscount = discountService.createOrUpdateDiscount(discount);
        return ResponseEntity.ok(savedDiscount);
    }

    /**
     * Retrieves a discount entity based on the provided discount type.
     *
     * @param discountType the unique identifier of the discount type to fetch the discount for.
     * @return a ResponseEntity containing the DiscountEntity object with the discount details for the specified type, wrapped in an HTTP status of OK.
     *         If no discount is found for the given type, an appropriate error status will be returned.
     */
    @GetMapping("/type/{discountType}")
    public ResponseEntity<DiscountEntity> getDiscountByType(@PathVariable String discountType) {
        log.info("Fetching discount for type: {}", discountType);
        DiscountEntity discount = discountService.getDiscountByType(discountType);
        return ResponseEntity.ok(discount);
    }

    /**
     * Deletes a discount based on the provided discount ID.
     *
     * @param id the unique identifier of the discount to be deleted.
     *           It should be a valid string representing the discount ID.
     * @return a ResponseEntity with an HTTP status of OK if the discount
     *         was successfully deleted, or an appropriate error status otherwise.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscount(@PathVariable String id) {
        log.info("Received request to delete discount for id: {}", id);
        discountService.deleteDiscount(id);
        return ResponseEntity.ok().build();
    }
}