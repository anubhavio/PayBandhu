package paybandhu.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paybandhu.agent.api.request.AgentRegistrationRequest;
import paybandhu.agent.api.response.AgentRegistrationResponse;
import paybandhu.agent.domain.Address;
import paybandhu.agent.domain.Agent;
import paybandhu.agent.domain.AgentDocument;
import paybandhu.agent.domain.AgentStatus;
import paybandhu.agent.repository.AgentRepository;
import paybandhu.common.Exception.DuplicateResourceException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AgentServiceImp implements AgentService{

    private final AgentRepository agentRepository;

    @Override
    public AgentRegistrationResponse registerAgent(AgentRegistrationRequest request, String ipAddress) {

        checkDuplicates(request);

        Address address = Address.builder()
                .state(request.getAddress().getState())
                .city(request.getAddress().getCity())
                .pinCode(request.getAddress().getPinCode())
                .streetAddress(request.getAddress().getStreetAddress())
                .build();


        Agent agent = Agent.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .mobileNumber(request.getMobileNumber())
                .emailAddress(request.getEmailAddress())
                .aadhaarNumber(request.getAadhaarNumber())
                .panNumber(request.getPanNumber())
                .address(address)
                .agentCode("AGT-"+System.currentTimeMillis())
                .status(AgentStatus.REGISTERED)
                .registrationIp(ipAddress)
                .build();

        Agent saved = agentRepository.save(agent);

        return AgentRegistrationResponse.builder()
                .id(saved.getId())
                .agentCode(saved.getAgentCode())
                .status(saved.getStatus())
                .message("Agent registered successfully. Pending KYC review")
                .build();
    }

    private void checkDuplicates(AgentRegistrationRequest request){

        Optional<Agent> existing = agentRepository.findByMobileNumber(request.getMobileNumber());

        if (existing.isPresent()){
            Agent agent = existing.get();
            switch (agent.getStatus()){
                case REGISTERED -> throw new DuplicateResourceException(
                        "Application was rejected. Reason: "+ agent.getAgentRejectionReason()
                );
                case ACTIVE -> throw new DuplicateResourceException(
                        "Mobile number is already registered");
                default -> throw new DuplicateResourceException(
                        "Application in progress");
            }
        }
        if(agentRepository.findByAadhaarNumber(request.getAadhaarNumber()).isPresent()){
            throw new DuplicateResourceException("Aadhaar number already registered");}
        if(agentRepository.findByPanNumber(request.getPanNumber()).isPresent()){
            throw new DuplicateResourceException("Pan number already registered");
        }
    }


    @Override
    public AgentRegistrationResponse verifyAgent(Long agentId) {
        return null;
    }

    @Override
    public AgentRegistrationResponse rejectAgent(Long agentId, String reason) {
        return null;
    }
}
