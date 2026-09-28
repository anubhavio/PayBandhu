package paybandhu.agent.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paybandhu.agent.api.request.AgentDocumentRequest;
import paybandhu.agent.api.request.AgentRegistrationRequest;
import paybandhu.agent.api.request.AgentRejectionReasonRequest;
import paybandhu.agent.api.response.*;
import paybandhu.agent.domain.*;
import paybandhu.agent.repository.AgentRepository;
import paybandhu.common.Exception.DuplicateResourceException;
import paybandhu.common.Exception.ResourceNotFoundException;
import paybandhu.security.domain.User;
import paybandhu.security.service.CurrentUserService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentServiceImp implements AgentService{

    private final AgentRepository agentRepository;

    private final CurrentUserService currentUserService;

    @Override
    public AgentProfileResponse getMyProfile() {

        User user = currentUserService.getCurrentUser();

        Agent agent = user.getAgent();

        if (agent == null) {
            throw new IllegalStateException(
                    "Current user is not associated with an agent"
            );
        }

        return AgentProfileResponse.builder()
                .agentCode(agent.getAgentCode())
                .basicDetails(
                        AgentBasicDetailsResponse.builder()
                                .firstName(agent.getFirstName())
                                .lastName(agent.getLastName())
                                .mobileNumber(agent.getMobileNumber())
                                .emailAddress(agent.getEmailAddress())
                                .dateOfBirth(agent.getDateOfBirth())
                                .build()
                )
                .address(AddressResponse.builder()
                        .pinCode(agent.getAddress().getPinCode())
                        .state(agent.getAddress().getState())
                        .city(agent.getAddress().getCity())
                        .streetAddress(agent.getAddress().getStreetAddress())
                        .build()

                ).status(agent.getStatus())
                .build();
    }
}
