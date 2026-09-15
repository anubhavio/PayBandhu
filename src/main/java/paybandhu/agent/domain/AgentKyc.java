package paybandhu.agent.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "agent_kyc")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public  class AgentKyc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "kyc_reference_number", length = 50, unique = true, nullable = false)
    private String kycReferenceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_application_id", nullable = false, unique = true)
    private AgentApplication agentApplication;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgentKycStatus status;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
}