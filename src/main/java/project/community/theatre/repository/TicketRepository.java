package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.community.theatre.model.EventEntity;
import project.community.theatre.model.TicketEntity;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<TicketEntity, Long> {

    Optional<TicketEntity> findByTicketNumber(String ticketNumber);
    @Query("SELECT e FROM EventEntity e WHERE e.id = :id")
    List<TicketEntity> findEventById(@Param("id") String id);
}