package project.community.theatre.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.model.UserEntity;

import java.util.List;

public interface UserService {
    UserEntity getUserById(String userId);

    List<UserEntity> getAllUsers();

    UserEntity getUserByEmail(String userEmail);

    @Transactional
    AuthResponse signup(SignupRequest request);
    
    @Transactional
    UserEntity updateUser(String userId, UserEntity updatedUser);

    AuthResponse login(LoginRequest request);

    UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;
}