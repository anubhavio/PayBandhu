package paybandhu.transaction.domain;

import jakarta.persistence.*;
import lombok.*;
import paybandhu.agent.domain.Agent;

import java.time.LocalDateTime;

@Entity
@Table(name = "aeps_transactions")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AepsTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_reference", nullable = false, unique = true)
    private String transactionReference;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agent_id", nullable = false)
    private Agent agent;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AepsTransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AepsTransactionStatus status;

    @Column(name = "customer_aadhaar", nullable = false, length = 20)
    private String customerAadhaar;

    @Column(name = "bank_code", nullable = false, length = 20)
    private String bankCode;

    @Column(name = "provider_reference")
    private String providerReference;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

}
