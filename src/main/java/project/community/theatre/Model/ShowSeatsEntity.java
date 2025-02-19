package project.community.theatre.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import project.community.theatre.enums.SeatType;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "showseats")
public class ShowSeatsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "seatNumber", nullable = false)
    private String seatNumber;

    @Column(name = "rate", nullable = false)
    private int rate;

    @Enumerated(EnumType.STRING)
    @Column(name = "seatType", nullable = false)
    private SeatType seatType;

    @Column(name = "is_Booked",columnDefinition = "bit(1) default 0", nullable = false)
    private boolean booked;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bookedAt")
    private Date bookedAt;

    @ManyToOne
    @JsonIgnore
    private ShowEntity show;

    @ManyToOne
    @JsonIgnore
    private TicketEntity ticket;

}
