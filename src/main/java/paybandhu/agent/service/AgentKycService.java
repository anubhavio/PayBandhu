package paybandhu.agent.service;

import paybandhu.agent.api.response.AgentKycResponse;
import paybandhu.agent.domain.AgentKyc;
import paybandhu.agent.domain.AgentKycStatus;

import java.util.List;

public interface AgentKycService {

    AgentKycResponse initiateKyc(Long applicationId);

    AgentKyc getKycByApplicationId(Long applicationId);

    AgentKyc getKycByReferenceNumber(String kycReferenceNumber);

    AgentKycResponse startKyc(Long applicationId);

    AgentKycResponse completeKyc(Long applicationId);

    AgentKycResponse verifyKyc(Long applicationId);

    AgentKyc rejectKyc(Long applicationId, String rejectionReason);

    AgentKyc failKyc(Long applicationId, String failureReason);

    List<AgentKyc> getKycByStatus(AgentKycStatus status);
}
