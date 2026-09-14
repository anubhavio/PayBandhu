package paybandhu.agentKyc;

import java.util.List;

public interface AgentKycService {

    AgentKyc initiateKyc(Long applicationId);

    AgentKyc getKycByApplicationId(Long applicationId);

    AgentKyc getKycByReferenceNumber(String kycReferenceNumber);

    AgentKyc completeKyc(Long applicationId);

    AgentKyc verifyKyc(Long applicationId);

    AgentKyc rejectKyc(Long applicationId, String rejectionReason);

    AgentKyc failKyc(Long applicationId, String failureReason);

    List<AgentKyc> getKycByStatus(AgentKycStatus status);
}
