package paybandhu.agent.domain;

import lombok.Getter;

@Getter
public enum AgentApplicationStatus {

    SUBMITTED,

    KYC_PENDING,

    KYC_COMPLETED,

    PENDING_APPROVAL,

    APPROVED,

    REJECTED,

    AGREEMENT_PENDING,

    AGREEMENT_ACCEPTED,

    ACTIVATION_PENDING,

    ACTIVATED
}
