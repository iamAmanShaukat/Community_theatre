package project.community.theatre.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.exception.UserAlreadyExistsException;
import project.community.theatre.exception.UserNotFoundException;
import project.community.theatre.model.UserEntity;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.UserService;
import project.community.theatre.util.JwtUtil;
import project.community.theatre.util.PasswordEncoderUtil;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private final JwtUtil jwtUtil;

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
                .userId(UUID.randomUUID().toString())
                .name(request.getName())
                .mobileNo(request.getMobileNo())
                .email(request.getEmail())
                .password(hashedPassword)
                .role(role)
                .build();
        userRepository.save(user);
        log.info("User with email {} saved successfully",request.getEmail());

        String token = jwtUtil.generateToken(user.getUserId(), user.getRole());

        return new AuthResponse("User registered successfully", user.getUserId(), user.getRole(), token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + request.getEmail()));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UserNotFoundException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getUserId(), user.getRole());

        return new AuthResponse("Login successful", user.getUserId(), user.getRole(), token);

    }

    @Override
    public UserEntity getUserById(String userId) {
        return userRepository.findUserById(userId).orElseThrow(() ->
                new UserNotFoundException("User not found with id: " + userId));
    }

    @Override
    public UserEntity getUserByEmail(String userEmail) {
        return userRepository.findByEmail(userEmail).orElseThrow(() ->
                new UserNotFoundException("User not found with email: " + userEmail));
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findUserById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with ID: " + userId));

        return new org.springframework.security.core.userdetails.User(
                user.getUserId(),
                user.getPassword(),
                Collections.singletonList(user::getRole)
        );
    }

    @Override
    public List<UserEntity> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}