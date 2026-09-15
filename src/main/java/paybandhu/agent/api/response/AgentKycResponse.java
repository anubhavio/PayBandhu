package paybandhu.agent.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.domain.AgentKycStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class AgentKycResponse {

    private Long id;

    private String kycReferenceNumber;

    private Long applicationId;

    private AgentKycStatus status;

    private String failureReason;

    private String rejectionReason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime submittedAt;

    private LocalDateTime verifiedAt;
}
