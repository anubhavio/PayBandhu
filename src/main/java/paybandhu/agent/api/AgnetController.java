package paybandhu.agent.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paybandhu.agent.api.request.AgentDocumentRequest;
import paybandhu.agent.api.request.AgentRegistrationRequest;
import paybandhu.agent.api.response.AgentDocumentResponse;
import paybandhu.agent.api.response.AgentRegistrationResponse;
import paybandhu.agent.service.AgentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agents")
@RequiredArgsConstructor
public class AgnetController {

    private final AgentService agentService;

    @PostMapping("/register")
    public ResponseEntity<AgentRegistrationResponse> register (
            @Valid @RequestBody AgentRegistrationRequest request,
            HttpServletRequest httpRequest)
    {
    String ipAddress = httpRequest.getRemoteAddr();

    AgentRegistrationResponse response = agentService.registerAgent(request, ipAddress);

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/{agentId}/documents")
    public ResponseEntity<AgentDocumentResponse> uploadDocument(
            @PathVariable Long agentId,
            @Valid @RequestBody List<AgentDocumentRequest> request) {


        AgentDocumentResponse response =
                agentService.uploadDocuments(request, agentId);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }




}
