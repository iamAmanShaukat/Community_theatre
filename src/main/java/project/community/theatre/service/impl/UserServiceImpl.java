package project.community.theatre.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.exception.UserAlreadyExistsException;
import project.community.theatre.exception.UserNotFoundException;
import project.community.theatre.model.UserEntity;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.UserService;
import project.community.theatre.util.PasswordEncoderUtil;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoderUtil passwordEncoder;

    private static final List<String> VALID_ROLES = List.of("USER", "ADMIN");

    @Transactional
    @Override
    public AuthResponse signup(SignupRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
        }

        String hashedPassword = passwordEncoder.encodePassword(request.getPassword());

        String role = request.getRole() != null && VALID_ROLES.contains(request.getRole()) ? request.getRole() : "USER";

        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .mobileNo(request.getMobileNo())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(role)
                .build();
        userRepository.save(user);

        return new AuthResponse("User registered successfully", user.getUserId(), user.getRole());
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }

        return new AuthResponse("Login successful", user.getUserId(), user.getRole());
    }
}