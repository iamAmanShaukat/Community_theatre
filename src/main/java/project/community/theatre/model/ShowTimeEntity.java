package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "event")
@Table(name = "event_show_times")
public class ShowTimeEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id = UUID.randomUUID().toString(); // Use String for ID

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(name = "show_time", nullable = false)
    private String showTime; // Store date-time as a string in Zulu format

    // Constructor to generate ID
    public ShowTimeEntity(EventEntity event, String showTime) {
        this.id = UUID.randomUUID().toString(); // Generate a unique ID
        this.event = event;
        this.showTime = showTime;
    }

    public ShowTimeEntity(String showId) {
        this.id = showId;
    }
}