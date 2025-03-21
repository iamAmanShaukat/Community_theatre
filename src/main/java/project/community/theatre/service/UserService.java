package project.community.theatre.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import project.community.theatre.dto.requestDto.LoginRequest;
import project.community.theatre.dto.requestDto.SignupRequest;
import project.community.theatre.dto.responseDto.AuthResponse;
import project.community.theatre.model.UserEntity;

import java.util.List;

/**
 * This interface defines the contract for user-related operations.
 */
public interface UserService {

    /**
     * Retrieves a user by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The user entity corresponding to the given identifier.
     */
    UserEntity getUserById(String userId);

    /**
     * Retrieves all users from the system.
     *
     * @return A list of all user entities.
     */
    List<UserEntity> getAllUsers();

    /**
     * Retrieves a user by their email address.
     *
     * @param userEmail The email address of the user.
     * @return The user entity corresponding to the given email address.
     */
    UserEntity getUserByEmail(String userEmail);

    /**
     * Registers a new user in the system.
     *
     * @param request The signup request containing user details.
     * @return The authentication response containing the access token.
     */
    @Transactional
    AuthResponse signup(SignupRequest request);

    /**
     * Updates an existing user's information.
     *
     * @param userId The unique identifier of the user.
     * @param updatedUser The updated user entity.
     * @return The updated user entity.
     */
    @Transactional
    UserEntity updateUser(String userId, UserEntity updatedUser);

    /**
     * Authenticates a user by their credentials.
     *
     * @param request The login request containing user credentials.
     * @return The authentication response containing the access token.
     */
    AuthResponse login(LoginRequest request);

    /**
     * Loads user details by their unique identifier.
     *
     * @param userId The unique identifier of the user.
     * @return The user details corresponding to the given identifier.
     * @throws UsernameNotFoundException If the user with the given identifier is not found.
     */
    UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;
}