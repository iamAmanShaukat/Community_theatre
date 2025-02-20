package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import project.community.theatre.model.MovieEntity;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity, Integer> {

    // Find movies by title
    List<MovieEntity> findByName(String name);

    // Find movies by genre
    List<MovieEntity> findByGenre(String genre);
}