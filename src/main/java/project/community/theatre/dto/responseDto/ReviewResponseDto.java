package project.community.theatre.dto.responseDto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDto {
    private String reviewId;
    private String userName;
    private Integer rating;
    private String description;
    private LocalDate reviewDate;

}
