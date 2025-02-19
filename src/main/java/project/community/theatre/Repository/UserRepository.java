package project.community.theatre.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.Model.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
    
}
