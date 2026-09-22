package paybandhu.agent.service;

import paybandhu.agent.api.response.AgentAgreementResponse;
import paybandhu.agent.domain.AgentAgreement;

public interface AgentAgreementService {

    AgentAgreementResponse initiateAgreement(Long applicationId);

    AgentAgreementResponse acceptAgreement(Long applicationId);
}
