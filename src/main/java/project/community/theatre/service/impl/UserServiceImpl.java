package project.community.theatre.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.community.theatre.exception.UserNotFoundException;
import project.community.theatre.model.UserEntity;
import project.community.theatre.repository.UserRepository;
import project.community.theatre.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity authenticateUser(String email, String password) {
        // Fetch the user by email
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        // Verify the password
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    @Override
    @Transactional
    public UserEntity registerUser(UserEntity user) {
        // Check if the email is already registered
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already registered: " + user.getEmail());
        }

        // Encode the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public UserEntity getUserById(int userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userId));
    }
}