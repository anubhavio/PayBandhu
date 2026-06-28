package paybandhu.agent.api.response;

import lombok.*;
import paybandhu.agent.domain.AgentStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentVerificationResponse {

    private Long id;

    private String agentCode;

    private AgentStatus status;

    private String message;

}
