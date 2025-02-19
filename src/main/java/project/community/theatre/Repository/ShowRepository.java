package project.community.theatre.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.Model.ShowEntity;

public interface ShowRepository extends JpaRepository<ShowEntity, Integer> {
    
}
