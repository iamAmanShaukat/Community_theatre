package project.community.theatre.mapper;

import project.community.theatre.dto.requestDto.EventEntryDto;
import project.community.theatre.dto.responseDto.EventResponseDto;
import project.community.theatre.model.EventEntity;

import java.time.LocalDate;

public class EventMapper {

    /**
     * Maps an EventEntryDto object to an EventEntity object.
     *
     * @param eventEntryDto The DTO containing event data to be mapped.
     * @return An EventEntity object populated with data from the provided DTO.
     * @throws IllegalArgumentException if the eventEntryDto is null.
     */
    public static EventEntity mapDtoToEntity(EventEntryDto eventEntryDto) {
        if (eventEntryDto == null) {
            throw new IllegalArgumentException("EventEntryDto cannot be null");
        }

        // Parse startDate and endDate into LocalDate (assuming format "yyyy-MM-dd")
        LocalDate startDate = LocalDate.parse(eventEntryDto.getStartDate());
        LocalDate endDate = LocalDate.parse(eventEntryDto.getEndDate());

        return EventEntity.builder()
                .eventId(eventEntryDto.getEventId())
                .name(eventEntryDto.getName())
                .genre(eventEntryDto.getGenre())
                .startDate(startDate)
                .endDate(endDate)
                .duration(eventEntryDto.getDuration())
                .description(eventEntryDto.getDescription())
                .producer(eventEntryDto.getProducer())
                .director(eventEntryDto.getDirector())
                .build();
    }

    public static EventResponseDto mapEntityToDto(EventEntity eventEntity) {
        if (eventEntity == null) {
            throw new IllegalArgumentException("EventEntity cannot be null");
        }
        return EventResponseDto.builder()
                .eventId(eventEntity.getEventId())
                .name(eventEntity.getName())
                .genre(eventEntity.getGenre())
                .startDate(eventEntity.getStartDate())
                .endDate(eventEntity.getEndDate())
                .duration(eventEntity.getDuration())
                .description(eventEntity.getDescription())
                .producer(eventEntity.getProducer())
                .director(eventEntity.getDirector())
                .imageUrl(eventEntity.getImageUrl())
                .build();
    }
}