package paybandhu.agent.api.response;


import lombok.*;
import paybandhu.agent.domain.AgentStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentRegistrationResponse {


    private Long id;

    private String agentCode;

    private AgentStatus status;

    private String message;
}
