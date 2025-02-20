package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.TheaterSeatEntity;

public interface TheaterSeatRepository extends JpaRepository<TheaterSeatEntity, Integer> {
    
}
