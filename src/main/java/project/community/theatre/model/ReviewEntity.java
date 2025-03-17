package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class ReviewEntity {
    @Id
    @Column(name = "review_id", nullable = false, unique = true)
    private String reviewId = UUID.randomUUID().toString();

    @Column(name = "user_name", nullable = false)
    private String userName; // Default to "Anonymous" if empty

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "reviewed_date", nullable = false)
    private LocalDate reviewedDate;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;
}