package project.community.theatre.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "movies")
public class MovieEntity {

    @Id
    @NotNull(message = "ID cannot be null")
    private String movieId;

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
}