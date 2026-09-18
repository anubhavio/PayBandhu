package paybandhu.agent.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paybandhu.agent.api.request.AgentKycDocumentRequest;
import paybandhu.agent.api.response.AgentKycDocumentResponse;
import paybandhu.agent.service.AgentKycDocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/agent-kyc")
@RequiredArgsConstructor
public class AgentKycDocumentController {

    private final AgentKycDocumentService agentKycDocumentService;

    @PostMapping("/{applicationId}/documents")
    public ResponseEntity<AgentKycDocumentResponse> uploadDocument(
            @PathVariable  Long applicationId,
            @Valid @RequestBody AgentKycDocumentRequest request){

        AgentKycDocumentResponse response = agentKycDocumentService
                .uploadDocument(applicationId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{applicationId}/documents")
    public ResponseEntity<List<AgentKycDocumentResponse>> getDocuments(
            @PathVariable Long applicationId ){

        return ResponseEntity.ok(
                agentKycDocumentService.getDocuments(applicationId)
        );

    }


}
