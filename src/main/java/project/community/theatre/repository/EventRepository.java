package project.community.theatre.repository;

import io.micrometer.observation.ObservationFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import project.community.theatre.model.EventEntity;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, Integer> {

    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    Optional<EventEntity> findEventById(@Param("id") String id);
}