package paybandhu.agent.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paybandhu.agent.api.response.AgentAgreementResponse;
import paybandhu.agent.domain.AgentAgreement;
import paybandhu.agent.service.AgentAgreementService;

@RestController
@RequestMapping("/api/agent-agreements")
@RequiredArgsConstructor
public class AgentAgreementController {

   private final AgentAgreementService agentAgreementService;

    @PostMapping("/{applicationId}/initiate")
    public ResponseEntity<AgentAgreementResponse> initiateAgreement(
            @PathVariable Long applicationId) {

        AgentAgreementResponse response =
                agentAgreementService.initiateAgreement(applicationId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{applicationId}/accept")
    public ResponseEntity<AgentAgreementResponse> acceptAgreement(
            @PathVariable Long applicationId) {

        AgentAgreementResponse response =
                agentAgreementService.acceptAgreement(applicationId);

        return ResponseEntity.ok(response);
    }
}
