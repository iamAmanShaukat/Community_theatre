package project.community.theatre.dto.requestDto;

import lombok.Data;

import java.util.List;

@Data
public class AddShowTimesRequestDto {

    private String eventId;
    private List<String> showTimes;
}