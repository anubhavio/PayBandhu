package paybandhu.agent.api.request;


import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.domain.Agent;
import paybandhu.agent.domain.DocumentType;

@Getter
@Setter
public class AgentDocumentRequest {

    private DocumentType documentType;

    private String fileName;

    private String fileUrl;

    private String contentType;

    private Long fileSize;

    private Agent agent;

}
