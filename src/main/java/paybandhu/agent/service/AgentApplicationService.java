package paybandhu.agent.service;

import paybandhu.agent.api.request.AgentApplicationRequest;
import paybandhu.agent.api.response.AgentApplicationResponse;

public interface AgentApplicationService {

    AgentApplicationResponse submitApplication(
            AgentApplicationRequest request
    );
}
