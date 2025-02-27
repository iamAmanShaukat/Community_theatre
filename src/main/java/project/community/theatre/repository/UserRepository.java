package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.community.theatre.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByEmail(String email);

    @Query("SELECT u FROM UserEntity u WHERE u.id = :id")
    Optional<UserEntity> findUserById(@Param("id") String id);
}