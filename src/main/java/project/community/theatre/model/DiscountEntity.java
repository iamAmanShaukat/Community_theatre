package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class DiscountEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "discount_type", nullable = false)
    @NotBlank(message = "Discount type cannot be blank")
    private String discountType; // e.g., "CHILD", "STUDENT", "PENSIONER"

    @Column(name = "discount_percentage", nullable = false)
    @NotNull(message = "Discount percentage cannot be null"  )
    private Double discountPercentage;

    //Add Constructor
    public DiscountEntity(String discountType, Double discountPercentage) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID  (UUID)
        this.discountType = discountType;
        this.discountPercentage = discountPercentage;
    }

}