package project.community.theatre.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "showTimes")
@Table(name = "events")
public class EventEntity {

    /**
     * Unique identifier for the event.
     * Cannot be null.
     */
    @Id
    @NotNull(message = "ID cannot be null")
    private String eventId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "genre", nullable = false)
    private String genre;

    @Column(name = "start_date", columnDefinition = "DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", columnDefinition = "DATE", nullable = false)
    private LocalDate endDate;

    @Column(name = "duration")
    private String duration;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "producer")
    private String producer;

    @Column(name = "director")
    private String director;

    @Column(name = "image_url")
    private String imageUrl;

    /**
     * List of show times associated with the event.
     * This is a one-to-many relationship with ShowTimeEntity.
     * The showTimes are mapped by the "event" field in ShowTimeEntity.
     * Cascading all operations to showTimes and removing orphaned showTimes.
     */
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ShowTimeEntity> showTimes;

    public EventEntity(String eventId) {
        this.eventId = eventId;
    }
}