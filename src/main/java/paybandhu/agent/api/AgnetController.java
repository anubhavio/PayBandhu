package paybandhu.agent.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paybandhu.agent.api.request.AgentDocumentRequest;
import paybandhu.agent.api.request.AgentRegistrationRequest;
import paybandhu.agent.api.request.AgentRejectionReasonRequest;
import paybandhu.agent.api.response.AgentDocumentResponse;
import paybandhu.agent.api.response.AgentRegistrationResponse;
import paybandhu.agent.api.response.AgentVerificationResponse;
import paybandhu.agent.domain.AgentRejectionReason;
import paybandhu.agent.service.AgentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/agents")
@RequiredArgsConstructor
public class AgnetController {

    private final AgentService agentService;



}
