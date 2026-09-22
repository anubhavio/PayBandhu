package paybandhu.agent.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import paybandhu.agent.api.response.AgentKycResponse;
import paybandhu.agent.domain.AgentKyc;
import paybandhu.agent.service.AgentKycService;

@RestController
@RequestMapping("/api/agent-kyc")
@RequiredArgsConstructor
public class AgentKycController {

    private final AgentKycService  agentKycService;

    @PostMapping("/{applicationId}/initiate")
    public ResponseEntity<AgentKycResponse> initiateKyc(
            @PathVariable Long applicationId){

        AgentKycResponse response  =
                agentKycService.initiateKyc(applicationId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{applicationId}/start-kyc")
    public ResponseEntity<AgentKycResponse> startKyc(
            @PathVariable Long applicationId){

        AgentKycResponse response=
                agentKycService.startKyc(applicationId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{applicationId}/complete-kyc")
    public ResponseEntity<AgentKycResponse> completeKyc(
            @PathVariable Long applicationId) {

        AgentKycResponse response =
                agentKycService.completeKyc(applicationId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{applicationId}/verify-kyc")
    public ResponseEntity<AgentKycResponse> verifyKyc(
            @PathVariable Long applicationId ){

        AgentKycResponse response =
                agentKycService.verifyKyc(applicationId);

        return ResponseEntity.ok(response);
    }
}
