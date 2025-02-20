package project.community.theatre.service;


import project.community.theatre.dto.requestDto.UserEntryDto;
import project.community.theatre.dto.responseDto.UserResponseDto;
import project.community.theatre.model.UserEntity;

import java.util.List;

public interface UserService {
    // Add User
    UserResponseDto addUser(UserEntryDto userEntryDto);

    // Get User
    UserResponseDto getUser(int id);

    // Get All Users
    List<UserEntity> getAllUser();
}
