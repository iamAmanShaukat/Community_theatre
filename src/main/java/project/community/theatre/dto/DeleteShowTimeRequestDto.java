package project.community.theatre.dto;

import lombok.Data;

@Data
public class DeleteShowTimeRequestDto {

    private String eventId;
    private String showTime; // Specific show time to delete
}