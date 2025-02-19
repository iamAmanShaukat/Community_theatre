package project.community.theatre.Converter;

import project.community.theatre.Dto.EntryRequestDto.UserEntryDto;
import project.community.theatre.Dto.ResponseDto.UserResponseDto;
import project.community.theatre.Model.UserEntity;

public class UserConverter {

    public static UserEntity convertDtoToEntity(UserEntryDto userEntryDto) {
        if (userEntryDto == null) {
            throw new IllegalArgumentException("UserEntryDto cannot be null");
        }
        return UserEntity.builder()
                .name(userEntryDto.getName())
                .mobileNo(userEntryDto.getMobNo()) // Ensure this matches the field name in UserEntity
                .build();
    }

    public static UserResponseDto convertEntityToDto(UserEntity user) {
        if (user == null) {
            throw new IllegalArgumentException("UserEntity cannot be null");
        }
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .mobNo(user.getMobileNo()) // Ensure this matches the field name in UserResponseDto
                .build();
    }
}