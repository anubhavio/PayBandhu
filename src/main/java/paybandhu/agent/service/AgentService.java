package paybandhu.agent.service;

import paybandhu.agent.api.request.AgentDocumentRequest;
import paybandhu.agent.api.request.AgentRegistrationRequest;
import paybandhu.agent.api.response.AgentDocumentResponse;
import paybandhu.agent.api.response.AgentRegistrationResponse;
import paybandhu.agent.domain.AgentRejectionReason;

import java.util.List;

public interface AgentService {

    AgentRegistrationResponse registerAgent(AgentRegistrationRequest request, String ipAddress);
    AgentDocumentResponse uploadDocuments(List<AgentDocumentRequest> documentRequest, Long agentId );
    AgentRegistrationResponse  verifyAgent(Long agentId);
    AgentRegistrationResponse rejectAgent(Long agentId, AgentRejectionReason reason);

}
