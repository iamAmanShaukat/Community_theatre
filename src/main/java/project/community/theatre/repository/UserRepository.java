package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);
}