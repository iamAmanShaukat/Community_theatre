package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a band entity in the community theatre system.
 * This class is used to store and retrieve band-related information from the database.
 *
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bands")
public class BandEntity {
    @Id
    @Column(name = "band_id", nullable = false, unique = true)
    @NotBlank(message = "Band ID cannot be empty or blank")
    private String bandId;

    @Column(name = "seats_per_band", nullable = false)
    private Integer seatsPerBand;

    @Column(name = "price", nullable = false)
    private Double price;

}