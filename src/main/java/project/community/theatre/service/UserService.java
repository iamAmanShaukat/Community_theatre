package project.community.theatre.service;

import jakarta.transaction.Transactional;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.model.UserEntity;

public interface UserService {

    @Transactional
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}