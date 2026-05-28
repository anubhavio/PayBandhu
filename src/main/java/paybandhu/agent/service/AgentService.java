package paybandhu.agent.service;

import paybandhu.agent.api.request.AgentDocumentRequest;
import paybandhu.agent.api.request.AgentRegistrationRequest;
import paybandhu.agent.api.response.AgentRegistrationResponse;

import java.util.List;

public interface AgentService {

    AgentRegistrationResponse registerAgent(AgentRegistrationRequest request, String ipAddress);
    AgentRegistrationResponse uploadDocuments(List<AgentDocumentRequest> documentRequest, Long agentId , String ipAddress);
    AgentRegistrationResponse  verifyAgent(Long agentId);
     AgentRegistrationResponse rejectAgent(Long agentId, String reason);
}
