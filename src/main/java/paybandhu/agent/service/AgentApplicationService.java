package paybandhu.agent.service;

import paybandhu.agent.api.request.AgentApplicationRequest;
import paybandhu.agent.api.response.AgentApplicationResponse;
import paybandhu.agent.api.response.AgentKycDocumentResponse;

import java.util.List;

public interface AgentApplicationService {

    AgentApplicationResponse submitApplication(
            AgentApplicationRequest request, String registrationIp
    );

    void submitForApproval(Long applicationId);

    void approveApplication(Long applicationId);

    void rejectApplication(Long applicationId, String reason);

    void moveToAgreementPending(Long applicationId);

    void moveToActivationPending(Long applicationId);

    void activateAgent(Long applicationId);

    List<AgentApplicationResponse> getApplications(
            Long applicationId
    );

}
