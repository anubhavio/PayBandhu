package paybandhu.agent.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "agent_kyc_documents")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentKycDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agent_kyc_id", nullable = false)
    private AgentKyc agentKyc;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycDocumentType documentType;

    @Column(name = "document_number")
    private String documentNumber;

    @Column(name = "file_reference")
    private String fileReference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KycDocumentStatus status;

    @Column(name = "rejection_reason", length = 500)
    private String rejectionReason;

}
