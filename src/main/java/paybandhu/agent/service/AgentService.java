package paybandhu.agent.service;

import paybandhu.agent.api.request.AgentRegistrationRequest;
import paybandhu.agent.api.response.AgentRegistrationResponse;

public interface AgentService {

    AgentRegistrationResponse registerAgent(AgentRegistrationRequest request, String ipAddress);
    AgentRegistrationResponse  verifyAgent(Long agentId);
     AgentRegistrationResponse rejectAgent(Long agentId, String reason);
}
