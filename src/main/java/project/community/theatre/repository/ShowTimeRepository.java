package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.ShowTimeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShowTimeRepository extends JpaRepository<ShowTimeEntity, Long> {
    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    Optional<EventEntity> findEventById(@Param("id") String id);

    List<ShowTimeEntity> findByEvent(EventEntity event);
}