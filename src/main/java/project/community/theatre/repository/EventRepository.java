package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import project.community.theatre.model.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    // Find movies by title
    List<EventEntity> findByName(String name);

    // Find movies by genre
    List<EventEntity> findByGenre(String genre);
}