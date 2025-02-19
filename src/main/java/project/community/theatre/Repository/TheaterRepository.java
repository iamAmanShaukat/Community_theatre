package project.community.theatre.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.Model.TheaterEntity;

public interface TheaterRepository extends JpaRepository<TheaterEntity,Integer> {
    
}
