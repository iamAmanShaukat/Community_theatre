package project.community.theatre.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.community.theatre.exception.ResourceNotFoundException;
import project.community.theatre.model.UserEntity;
import project.community.theatre.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * Retrieves a user entity by its unique identifier.
     *
     * @param userId the unique identifier of the user to be retrieved. Must not be null or empty.
     * @return a ResponseEntity containing the user entity if found, or an appropriate error response if not found.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<UserEntity> getUserById(@PathVariable String userId) {
        log.info("Received request to fetch user by ID: {}", userId);
        try {
            UserEntity user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (ResourceNotFoundException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(404).body(null);
        }
    }

    /**
     * Retrieves all user entities from the database.
     *
     * @return a ResponseEntity containing a list of user entities, or an appropriate error response if the list is empty.
     */
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        log.info("Received request to fetch all users");
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * Updates a user entity with the provided request body.
     *
     * @param userId    the unique identifier of the user to be updated. Must not be null or empty.
     * @param updatedUser the updated user entity to be saved. Must not be null.
     *
     * @return a ResponseEntity containing the updated user entity, or an appropriate error response if not found.
     */
    @PutMapping("/{userId}/update")
    public ResponseEntity<UserEntity> updateUser(@PathVariable String userId, @RequestBody UserEntity updatedUser) {
    UserEntity updated = userService.updateUser(userId, updatedUser);
    return ResponseEntity.ok(updated);
}


}