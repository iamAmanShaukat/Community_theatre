package project.community.theatre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import project.community.theatre.filter.JwtRequestFilter;

import java.util.Arrays;
import java.util.Collections;

/**
 * Configuration class for setting up security in the application.
 * This class is responsible for configuring web security settings,
 * including CORS, CSRF, and authentication rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Constructs a new SecurityConfig with the specified JWT request filter.
     *
     * @param jwtRequestFilter The JWT request filter to be used for authentication.
     */
    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Configures the security filter chain for the application.
     * This method sets up CSRF protection, CORS, request authorization rules,
     * session management, and adds the JWT request filter.
     *
     * @param http The HttpSecurity object to be configured.
     * @return A SecurityFilterChain object representing the configured security filters.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/auth/signup", "/api/v1/auth/login", "/event/get-all-events").permitAll()
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Configures and provides a CORS configuration source.
     * This method sets up CORS settings including allowed origins, methods, headers,
     * credentials, and max age for preflight requests.
     *
     * @return A CorsConfigurationSource object with the configured CORS settings.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setAllowCredentials(true); // true if using cookies or JWT tokens
        configuration.setMaxAge(3600L); // Cache preflight for 1 hour
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}