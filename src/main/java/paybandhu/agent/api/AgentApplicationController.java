package paybandhu.agent.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
