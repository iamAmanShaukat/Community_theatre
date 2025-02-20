package project.community.theatre.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//
//import java.util.Date;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@ToString
//@Table(name="tickets")
//public class TicketEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name = "allotedSeat", nullable = false)
//    private String allotedSeats;
//
//    @Column(name = "amount", nullable = false)
//    private double amount;
//
//    @CreationTimestamp
//    @Column(name = "bookedAt", nullable = false)
//    private Date bookedAt;
//
//    @ManyToOne
//    @JsonIgnore
//    @JoinColumn
//    private UserEntity user;
//
//    @ManyToOne
//    @JsonIgnore
//    private ShowEntity show;
//
//    @OneToMany (mappedBy = "show", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<ShowSeatsEntity> seatList;
//
//}
