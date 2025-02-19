package project.community.theatre.Converter;


import project.community.theatre.Dto.EntryRequestDto.UserEntryDto;
import project.community.theatre.Dto.ResponseDto.UserResponseDto;
import project.community.theatre.Model.UserEntity;

public class UserConverter {
    public static UserEntity convertDtoToEntity(UserEntryDto userEntryDto) {
        return UserEntity.builder()
            .name(userEntryDto.getName())
            .mobileNo(userEntryDto.getMobNo()).build();
    }

    public static UserResponseDto convertEntityToDto(UserEntity user) {
        return UserResponseDto.builder()
                .id(user.getId()).name(user.getName())
                .mobNo(user.getMobileNo()).build();
    }
}
