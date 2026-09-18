package paybandhu.agent.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.domain.KycDocumentStatus;
import paybandhu.agent.domain.KycDocumentType;

@Getter
@Setter
@Builder
public class AgentKycDocumentResponse {

    private Long id;

    private Long kycId;

    private KycDocumentType documentType;

    private KycDocumentStatus status;

    private String fileReference;
}
