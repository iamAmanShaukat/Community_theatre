package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "users")
public class UserEntity {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private String userId = UUID.randomUUID().toString();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "mobileNo", nullable = false)
    private String mobileNo;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "role", nullable = false)
    private String role;

    public UserEntity(String userId) {
        this.userId = userId;
    }
}