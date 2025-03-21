package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.service.UserService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    /**
     * Handles user signup requests.
     * 
     * This method processes a signup request, creates a new user account,
     * and returns an authentication response.
     *
     * @param request The SignupRequest object containing user registration details.
     * @return ResponseEntity<AuthResponse> A ResponseEntity containing the AuthResponse
     *         with details such as authentication token and user information.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> signup(@RequestBody SignupRequest request) {
        log.info("Received request to signup with email: {}", request.getEmail());
        AuthResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Handles user login requests.
     * 
     * This method authenticates a user based on the provided login credentials
     * and returns an authentication response.
     *
     * @param request The LoginRequest object containing user login credentials.
     * @return ResponseEntity<AuthResponse> A ResponseEntity containing the AuthResponse
     *         with details such as authentication token and user information.
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        log.info("Received request to login with email: {}", request.getEmail());
        AuthResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }
}