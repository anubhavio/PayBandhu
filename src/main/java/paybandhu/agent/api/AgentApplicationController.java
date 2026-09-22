package paybandhu.agent.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;
import paybandhu.agent.api.request.AgentApplicationRequest;
import paybandhu.agent.api.response.AgentApplicationResponse;
import paybandhu.agent.service.AgentApplicationService;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/agent-applications")
@RequiredArgsConstructor
public class AgentApplicationController {

    private final AgentApplicationService agentApplicationService;

    @PostMapping
    public ResponseEntity<AgentApplicationResponse> submitApplication(
            @Valid @RequestBody AgentApplicationRequest agentApplicationRequest
            , HttpServletRequest httpRequest
            ){

        String registrationIp = httpRequest.getRemoteAddr();

        AgentApplicationResponse response =
                agentApplicationService.submitApplication(agentApplicationRequest, registrationIp);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/{applicationId}/submit-for-approval")
    public ResponseEntity<Void> submitForApproval(
            @PathVariable Long applicationId ){

        agentApplicationService.submitForApproval(applicationId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{applicationId}/approve")
    public ResponseEntity<Void> approveApplication(
            @PathVariable Long applicationId ){

        agentApplicationService.approveApplication(applicationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{applicationId}/move-to-agreement-pending")
    private ResponseEntity<Void> moveToAgreementPending(
            @PathVariable Long applicationId ){

        agentApplicationService.moveToAgreementPending(applicationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{applicationId}/move-to-activation-pending")
    public ResponseEntity<Void> moveToActivationPending(
            @PathVariable Long applicationId) {

        agentApplicationService.moveToActivationPending(applicationId);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/{applicationId}/activate")
    public ResponseEntity<Void> activateAgent(
            @PathVariable Long applicationId) {

        agentApplicationService.activateAgent(applicationId);

        return ResponseEntity.ok().build();
    }
}
