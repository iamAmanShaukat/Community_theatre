package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.ReviewEntity;

import java.util.List;

public interface ReviewRepository extends JpaRepository<ReviewEntity, String> {
    List<ReviewEntity> findByEvent_EventId(String eventId);
}