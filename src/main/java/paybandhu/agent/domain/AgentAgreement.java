package paybandhu.agent.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "agent_agreement")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AgentAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agreement_reference_number", length = 50, unique = true, nullable = false)
    private String agreementReferenceNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_application_id", nullable = false, unique = true)
    private AgentApplication agentApplication;

    @Column(name = "agreement_version", nullable = false, length = 20)
    private String agreementVersion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgreementStatus status;

    @Column(name = "agreement_file_reference", length = 500)
    private String agreementFileReference;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "accepted_at")
    private LocalDateTime acceptedAt;

    @Column(name = "rejected_at")
    private LocalDateTime rejectedAt;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;
}
