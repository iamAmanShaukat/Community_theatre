package project.community.theatre.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import project.community.theatre.mapper.UserConverter;
import project.community.theatre.dto.requestDto.UserEntryDto;
import project.community.theatre.dto.responseDto.UserResponseDto;
import project.community.theatre.model.UserEntity;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.UserService;

import java.util.List;

@Service
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserResponseDto addUser(UserEntryDto userEntryDto) {
        UserEntity userEntity = UserConverter.convertDtoToEntity(userEntryDto);
        userRepository.save(userEntity);
        return UserConverter.convertEntityToDto(userEntity);
    }

    @Override
    public UserResponseDto getUser(int id) {
        UserEntity user = userRepository.findById(id).get();

        UserResponseDto userResponseDto = UserConverter.convertEntityToDto(user);

        return userResponseDto;
    }

    @Override
    public List<UserEntity> getAllUser() {
        List<UserEntity> usersList = userRepository.findAll();
        return usersList;
    }
    
}



























