package project.community.theatre.service;


import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;

import java.util.List;

public interface EventService {
    // Add Movie
    EventResponseDto addEvent(EventEntryDto eventEntryDto);
    
    // Get Movie
    EventResponseDto getEvent(String id);

    List<EventResponseDto> getAllEvent();

}
