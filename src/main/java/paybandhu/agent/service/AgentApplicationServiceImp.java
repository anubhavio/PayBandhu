package paybandhu.agent.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paybandhu.agent.api.request.AgentApplicationRequest;
import paybandhu.agent.api.response.AgentApplicationResponse;
import paybandhu.agent.domain.Address;
import paybandhu.agent.domain.AgentApplication;
import paybandhu.agent.domain.AgentApplicationStatus;
import paybandhu.agent.repository.AgentApplicationRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentApplicationServiceImp implements AgentApplicationService{

    private final AgentApplicationRepository agentApplicationRepository;

    @Override
    @Transactional
    public AgentApplicationResponse submitApplication(AgentApplicationRequest request,
                                                      String registrationIp) {

        //Check duplicate mobile number
        if(agentApplicationRepository.existsByMobileNumber
                (request.getMobileNumber()))
            throw new IllegalArgumentException(
                    "An application already exists with this mobile number"
            );

        //Check duplicate Aadhaar
        if(agentApplicationRepository.existsByAadhaarNumber
                (request.getAadhaarNumber()))
            throw new IllegalArgumentException(
                    "An application already exists with this Aadhaar number "
            );

        //Check duplicate Pan
        if (agentApplicationRepository.existsByPanNumber
                (request.getPanNumber()))
            throw new IllegalArgumentException(
                    "An application already exists with this Pan number "
            );

        //Check duplicate Email
        if (agentApplicationRepository.existsByEmailAddress(request.getEmailAddress()))
            throw new IllegalArgumentException(
                    "An application already exists with this email address"
            );

        Address address = Address.builder()
                .state(request.getAddress().getState())
                .city(request.getAddress().getCity())
                .pinCode(request.getAddress().getPinCode())
                .streetAddress(request.getAddress().getStreetAddress())
                .build();

        //Create Application
        AgentApplication application = AgentApplication.builder()
                .applicationNumber(generateApplicationNumber())
                .firstName(request.getFirstName())
                .middleName(request.getMiddleName())
                .lastName(request.getLastName())
                .mobileNumber(request.getMobileNumber())
                .emailAddress(request.getEmailAddress())
                .aadhaarNumber(request.getAadhaarNumber())
                .panNumber(request.getPanNumber())
                .dateOfBirth(request.getDateOfBirth())
                .gender(request.getGender())
                .address(address)
                .registrationIp(registrationIp)
                .status(AgentApplicationStatus.SUBMITTED)
                .build();

        AgentApplication savedApplication =
                agentApplicationRepository.save(application);

        return mapToResponse(savedApplication);
    }

    @Override
    @Transactional
    public void submitForApproval(Long applicationId) {

        AgentApplication application = agentApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Agent application not found: "+applicationId)
                );

        if(application.getStatus() != AgentApplicationStatus.KYC_COMPLETED){
            throw new IllegalStateException(
                    "Application can only be submitted for approval after kyc is completed"
            );
        }

        application.setStatus(AgentApplicationStatus.PENDING_APPROVAL);

        agentApplicationRepository.save(application);
    }


    @Override
    @Transactional
    public void approveApplication(Long applicationId) {

        AgentApplication application = agentApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Agent application not found: "+applicationId)
                );

        if(application.getStatus() != AgentApplicationStatus.PENDING_APPROVAL){
            throw new IllegalStateException(
                    "Application can only be approved when it is PENDING_APPROVAL"
            );
        }

        application.setStatus(AgentApplicationStatus.APPROVED);
        agentApplicationRepository.save(application);
    }

    @Override
    public void rejectApplication(Long applicationId, String reason) {

    }

    @Override
    @Transactional
    public void moveToAgreementPending(Long applicationId) {

        AgentApplication application =
                agentApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Agent application not found: " + applicationId
                                ));

        if (application.getStatus() != AgentApplicationStatus.APPROVED) {
            throw new IllegalStateException(
                    "Agreement can only be initiated for an approved application"
            );
        }

        application.setStatus(AgentApplicationStatus.AGREEMENT_PENDING);
        agentApplicationRepository.save(application);
    }

    @Override
    @Transactional
    public void moveToActivationPending(Long applicationId) {

        AgentApplication application =
                agentApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Agent application not found: " + applicationId
                                ));

        if (application.getStatus()
                != AgentApplicationStatus.AGREEMENT_ACCEPTED) {

            throw new IllegalStateException(
                    "Application can only move to activation when " +
                            "the agreement is accepted"
            );
        }

        application.setStatus(
                AgentApplicationStatus.ACTIVATION_PENDING
        );

        agentApplicationRepository.save(application);
    }

    @Override
    public List<AgentApplicationResponse> getApplications(Long applicationId) {
        return List.of();
    }

    private String generateApplicationNumber(){

        return "AG-APP-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0,8)
                        .toUpperCase();
    }

    private AgentApplicationResponse mapToResponse(AgentApplication application){

        return AgentApplicationResponse.builder()
                .id(application.getId())
                .applicationNumber(application.getApplicationNumber())
                .firstName(application.getFirstName())
                .lastName(application.getLastName())
                .mobileNumber(application.getMobileNumber())
                .emailAddress(application.getEmailAddress())
                .status(application.getStatus())
                .createdAt(application.getCreatedAt())
                .build();
    }
}
