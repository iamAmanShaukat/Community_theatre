package project.community.theatre.Service;


import project.community.theatre.Dto.EntryRequestDto.UserEntryDto;
import project.community.theatre.Dto.ResponseDto.UserResponseDto;
import project.community.theatre.Model.UserEntity;

import java.util.List;

public interface UserService {
    // Add User
    UserResponseDto addUser(UserEntryDto userEntryDto);

    // Get User
    UserResponseDto getUser(int id);

    // Get All Users
    List<UserEntity> getAllUser();
}
