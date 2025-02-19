package project.community.theatre.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="shows")
public class ShowEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "showDate", columnDefinition = "DATE", nullable = false)
    private LocalDate showDate;

    @Column(name = "showTime", columnDefinition = "TIME", nullable = false)
    private LocalTime showTime;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "createdOn")
    private Date createdOn;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    @Column(name = "updatedAt")
    Date updatedAt;

    @ManyToOne
    @JsonIgnore
    private MovieEntity movie;

    @ManyToOne
    @JsonIgnore
    private TheaterEntity theater;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<TicketEntity> ticketList;

    @OneToMany(mappedBy = "show", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ShowSeatsEntity> showSeatList;
}
