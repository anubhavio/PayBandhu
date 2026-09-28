package paybandhu.agent.api.response;

import lombok.*;
import paybandhu.agent.domain.AgentStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentProfileResponse {

    private String agentCode;

    private AgentBasicDetailsResponse basicDetails;

    private AddressResponse address;

    private AgentStatus status;

}
