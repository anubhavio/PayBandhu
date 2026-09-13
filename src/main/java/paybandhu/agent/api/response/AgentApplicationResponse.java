package paybandhu.agent.api.response;

import lombok.*;
import paybandhu.agent.domain.AgentApplicationStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentApplicationResponse {

    private Long id;

    private String applicationNumber;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String emailAddress;

    private AgentApplicationStatus status;

    private LocalDateTime createdAt;

}
