package project.community.theatre.model;

import jakarta.persistence.*;
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
    private String discountType; // e.g., "CHILD", "STUDENT", "SENIOR"

    @Column(name = "discount_percentage", nullable = false)
    private Double discountPercentage;

    //Add Constructor
    public DiscountEntity(String discountType, Double discountPercentage) {
        this.id = UUID.randomUUID().toString();  // Generate a unique ID  (UUID)
        this.discountType = discountType;
        this.discountPercentage = discountPercentage;
    }

}