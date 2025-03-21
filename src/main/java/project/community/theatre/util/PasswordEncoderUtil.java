package project.community.theatre.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * A utility class for encoding and matching passwords using BCrypt.
 * This class is a Spring component, which means it can be injected into other classes.
 */
@Component
public class PasswordEncoderUtil {

    /**
     * A static instance of BCryptPasswordEncoder for encoding passwords.
     */
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    /**
     * Encodes a raw password using BCrypt.
     *
     * @param rawPassword The raw password to be encoded.
     * @return The encoded password.
     */
    public String encodePassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    /**
     * Checks if a raw password matches an encoded password.
     *
     * @param rawPassword The raw password to be checked.
     * @param encodedPassword The encoded password to be matched.
     * @return True if the raw password matches the encoded password, false otherwise.
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
}