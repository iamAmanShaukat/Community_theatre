package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    
}
