package paybandhu.agent.api.response;

import lombok.*;
import paybandhu.agent.domain.AgentStatus;
import paybandhu.agent.domain.DocumentType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentDocumentResponse {

    private Long id;

    private String agentCode;

    private AgentStatus status;

    private DocumentType documentType;

    private String fileName;

    private String message;
}
