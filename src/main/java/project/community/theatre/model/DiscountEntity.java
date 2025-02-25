package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discounts")
public class DiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "discount_type", nullable = false)
    private String discountType; // e.g., "CHILD", "STUDENT", "SENIOR"

    @Column(name = "discount_percentage", nullable = false)
    private Double discountPercentage;

}