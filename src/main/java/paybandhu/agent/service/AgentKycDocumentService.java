package paybandhu.agent.service;

import paybandhu.agent.api.request.AgentKycDocumentRequest;
import paybandhu.agent.api.response.AgentKycDocumentResponse;

import java.util.List;

public interface AgentKycDocumentService {

    AgentKycDocumentResponse uploadDocument(
            Long applicationId,
            AgentKycDocumentRequest request
    );

    List<AgentKycDocumentResponse> getDocuments(
            Long applicationId
    );

    AgentKycDocumentResponse verifyDocument(
            Long applicationId,
            Long documentId
    );

    AgentKycDocumentResponse rejectDocument(
            Long applicationId,
            Long documentId,
            String reason
    );
}
