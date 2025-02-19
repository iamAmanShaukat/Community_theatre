package project.community.theatre.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.Model.TheaterSeatEntity;

public interface TheaterSeatRepository extends JpaRepository<TheaterSeatEntity, Integer> {
    
}
