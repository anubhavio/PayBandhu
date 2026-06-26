package paybandhu.agent.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import paybandhu.agent.api.request.AgentDocumentRequest;
import paybandhu.agent.api.request.AgentRegistrationRequest;
import paybandhu.agent.api.response.AgentDocumentResponse;
import paybandhu.agent.api.response.AgentRegistrationResponse;
import paybandhu.agent.domain.*;
import paybandhu.agent.repository.AgentRepository;
import paybandhu.common.Exception.DuplicateResourceException;
import paybandhu.common.Exception.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentServiceImp implements AgentService{

    private final AgentRepository agentRepository;


    @Override
    @Transactional
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
                .gender(request.getGender())
                .dateOfBirth(request.getDateOfBirth())
                .address(address)
                .agentCode("AGT-"+ UUID.randomUUID().toString().substring(0, 8).toUpperCase())
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

    @Override
    public AgentDocumentResponse uploadDocuments(List<AgentDocumentRequest> documentRequest,
                                                 Long agentId) {

        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agent not found with id: " + agentId));

        if (agent.getStatus() != AgentStatus.REGISTERED) {
            throw new IllegalStateException(
                    "Documents can only be uploaded by agents in REGISTERED status. Current status: "
                            + agent.getStatus());
        }

        documentRequest.stream()
                .map(document -> AgentDocument.builder()
                        .documentType(document.getDocumentType())
                        .fileName(document.getFileName())
                        .fileUrl(document.getFileUrl())
                        .contentType(document.getContentType())
                        .fileSize(document.getFileSize())
                        .uploadedAt(LocalDateTime.now())
                        .documentStatus(DocumentStatus.IN_REVIEW)
                        .agent(agent)
                        .build())
                .forEach(doc -> agent.getDocuments().add(doc));

        agent.setStatus(AgentStatus.PENDING_REVIEW);

        Agent saved = agentRepository.save(agent);

        return AgentDocumentResponse.builder()
                .id(saved.getId())
                .agentCode(saved.getAgentCode())
                .status(saved.getStatus())
                .message("Documents uploaded successfully. Pending review.")
                .build();
    }

    private void checkDuplicates(AgentRegistrationRequest request){

        Optional<Agent> existing = agentRepository.findByMobileNumber(request.getMobileNumber());

        if (existing.isPresent()){
            Agent agent = existing.get();
            switch (agent.getStatus()){
                case REJECTED -> throw new DuplicateResourceException(
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
        Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new ResourceNotFoundException(
                "Agent not found" + agentId
        ));
        if (agent.getStatus() != AgentStatus.PENDING_REVIEW) {
            throw new IllegalStateException("Only agent who uploaded documents can be verified");
        }

        for(AgentDocument doc : agent.getDocuments()){
            doc.setDocumentStatus(DocumentStatus.VERIFIED);
        }
        agent.setStatus(AgentStatus.ACTIVE);
        agent.setVerifiedAt(LocalDateTime.now());
        Agent saved = agentRepository.save(agent);

 return AgentRegistrationResponse.builder()
         .id(saved.getId())
         .agentCode(saved.getAgentCode())
         .status(saved.getStatus())
         .message("Agent verified successfully. now agent is ACTIVE")
         .build();


    }



    @Override
    public AgentRegistrationResponse rejectAgent(Long agentId, AgentRejectionReason reason) {
        Agent agent = agentRepository.findById(agentId).orElseThrow(()
                -> new ResourceNotFoundException("Agent not present "+agentId));
        if(agent.getStatus() != AgentStatus.PENDING_REVIEW){
            throw new IllegalStateException("Only Review pending agent can be rejected");
        }
        for(AgentDocument doc : agent.getDocuments()){
            doc.setDocumentStatus(DocumentStatus.REJECTED);
        }
        agent.reject(reason);
        Agent saved = agentRepository.save(agent);
        return AgentRegistrationResponse.builder()
                .id(saved.getId())
                .agentCode(saved.getAgentCode())
                .status(saved.getStatus())
                .message("Agent rejected "+ reason )
                .build();
    }
}
