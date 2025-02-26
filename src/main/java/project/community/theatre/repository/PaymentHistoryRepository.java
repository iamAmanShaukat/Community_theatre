package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.PaymentHistoryEntity;

public interface PaymentHistoryRepository extends JpaRepository<PaymentHistoryEntity, Long> {
}