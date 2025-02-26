package project.community.theatre.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShowTimeResponseDto {

    private String id; // Primary key of the ShowTimeEntity
    private String showTime; // Show time in Zulu format (e.g., "2025-02-20T14:30:00Z")
}