package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;
import project.community.theatre.enums.SeatBand;

import java.util.UUID;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="theaterSeats")
public class TheaterSeatEntity {
    @Id
    private String id = UUID.randomUUID().toString();

    @Column(name = "seatNumber", nullable = false)
    private String seatNumber;

    @Column(name = "price", nullable = false)
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "seatBand", nullable = false)
    private SeatBand seatBand;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    // Constructor to generate ID
    public TheaterSeatEntity(String seatNumber, double price, SeatBand seatBand, EventEntity event) {
        this.id = UUID.randomUUID().toString();
        this.seatNumber = seatNumber;
        this.price = price;
        this.seatBand = seatBand;
        this.event = event;
    }
}
