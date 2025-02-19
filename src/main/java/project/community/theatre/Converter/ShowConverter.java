package project.community.theatre.Converter;


import project.community.theatre.Dto.EntryRequestDto.ShowEntryDto;
import project.community.theatre.Dto.ResponseDto.ShowResponseDto;
import project.community.theatre.Model.ShowEntity;

public class ShowConverter {
    public static ShowEntity convertDtoToEntity(ShowEntryDto showEntryDto) {
        return ShowEntity.builder().showDate(showEntryDto.getShowDate()).
        showTime(showEntryDto.getShowTime()).build();
    }
    
    public static ShowResponseDto convertEntityToDto(ShowEntity showEntity){

        return ShowResponseDto.builder()
                .id(showEntity.getId())
                .showTime(showEntity.getShowTime())
                .showDate(showEntity.getShowDate())
                .build();
    }
}
