package project.community.theatre.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDto {
    private String movieId;
    private String name;
    private String genre;
    private LocalDate startDate;
    private LocalDate endDate;
    private String duration;
    private String description;
    private String producer;
    private String director;
    private String imageUrl;
}