package project.community.theatre.dto;

import lombok.Data;
import project.community.theatre.model.ShowTimeEntity;

import java.util.List;

@Data
public class AddShowTimesRequestDto {

    private String eventId;
    private List<String> showTimes;
}