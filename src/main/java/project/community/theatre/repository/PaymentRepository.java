package project.community.theatre.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.community.theatre.model.PaymentEntity;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}