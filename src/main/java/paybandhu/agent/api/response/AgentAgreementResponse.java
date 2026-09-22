package paybandhu.agent.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.domain.AgreementStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AgentAgreementResponse {

    private Long id;

    private String agreementReferenceNumber;

    private Long applicationId;

    private String agreementVersion;

    private AgreementStatus status;

    private String agreementFileReference;

    private LocalDateTime createdAt;

    private LocalDateTime acceptedAt;

    private LocalDateTime rejectedAt;

    private String rejectionReason;
}
