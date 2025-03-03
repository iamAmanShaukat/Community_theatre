package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
@ToString(exclude = {"user", "event"})
public class TicketEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventEntity event;

    @Column(name = "ticket_number", nullable = false, unique = true)
    private String ticketNumber;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "seat_numbers", nullable = false)
    private String seatNumbers;

    @Column(name = "show_time", nullable = false)
    private LocalDateTime showTime;

    @Column(name = "booking_time", nullable = false)
    private LocalDateTime bookingTime = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    public enum TicketStatus {
        BOOKED, CANCELLED
    }

    // Constructor to generate ID
    public TicketEntity(UserEntity user, EventEntity event, String ticketNumber, Double totalPrice,
                        String seatNumbers, LocalDateTime showTime, TicketStatus status) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.event = event;
        this.ticketNumber = ticketNumber;
        this.totalPrice = totalPrice;
        this.seatNumbers = seatNumbers;
        this.showTime = showTime;
        this.status = status;
    }
}