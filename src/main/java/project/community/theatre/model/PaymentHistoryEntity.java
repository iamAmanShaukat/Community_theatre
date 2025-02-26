package project.community.theatre.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payment_history")
public class PaymentHistoryEntity {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    private String id = UUID.randomUUID().toString(); // Use String for ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "transaction_id", nullable = false, unique = true)
    private String transactionId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;

    public enum PaymentStatus {
        SUCCESS, FAILED
    }

    // Constructor to generate ID
    public PaymentHistoryEntity(UserEntity user, String transactionId, Double amount,
                                LocalDateTime paymentTime, PaymentStatus status) {
        this.id = UUID.randomUUID().toString();
        this.user = user;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentTime = paymentTime;
        this.status = status;
    }
}