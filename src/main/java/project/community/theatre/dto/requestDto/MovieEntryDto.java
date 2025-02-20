package project.community.theatre.dto.requestDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
public class MovieEntryDto {
    @NotNull(message = "ID cannot be null")
    private String movieId;
    private String name;
    private String genre;
    private String startDate;
    private String endDate;
    private String duration;
    private String description;
    private String producer;
    private String director;
    private MultipartFile image;
}
