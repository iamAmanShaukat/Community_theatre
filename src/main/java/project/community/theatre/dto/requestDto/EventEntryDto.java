package project.community.theatre.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class EventEntryDto {
    @NotNull(message = "ID cannot be null")
    private String eventId;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Genre cannot be null")
    private String genre;
    @NotNull(message = "Start date cannot be null")
    private String startDate;
    @NotNull(message = "End date cannot be null")
    private String endDate;
    @NotNull(message = "Duration cannot be null")
    private String duration;
    private String description;
    private String producer;
    private String director;
    private MultipartFile image;
}
