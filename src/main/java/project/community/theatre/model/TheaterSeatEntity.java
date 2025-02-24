package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;
import project.community.theatre.enums.SeatBand;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="theaterSeats")
public class TheaterSeatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "seatNumber", nullable = false)
    private String seatNumber;

    @Column(name = "price", nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(name = "seatBand", nullable = false)
    private SeatBand seatBand;
}
