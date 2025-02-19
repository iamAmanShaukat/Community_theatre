package project.community.theatre.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.Model.ShowSeatsEntity;

public interface ShowSeatRepository extends JpaRepository<ShowSeatsEntity,Integer> {
    
}
