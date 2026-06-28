package paybandhu.agent.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.domain.AgentRejectionReason;

@Getter
@Setter
public class AgentRejectionReasonRequest {
                                                              
    @NotNull
    private AgentRejectionReason reason;

}
