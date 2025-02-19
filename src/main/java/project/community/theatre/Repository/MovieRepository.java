package project.community.theatre.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.Model.MovieEntity;

public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {
    
}
