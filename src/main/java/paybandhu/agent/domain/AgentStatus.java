package paybandhu.agent.domain;

import lombok.Getter;

@Getter
public enum AgentStatus {
    REGISTERED,
    PENDING_REVIEW,
    ACTIVE,
    REJECTED,
    BLOCKED
}
