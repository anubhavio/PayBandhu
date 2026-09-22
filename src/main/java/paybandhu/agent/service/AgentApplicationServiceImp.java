package paybandhu.agent.service;


import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paybandhu.agent.api.request.AgentApplicationRequest;
import paybandhu.agent.api.response.AgentApplicationResponse;
import paybandhu.agent.domain.*;
import paybandhu.agent.repository.AgentApplicationRepository;
import paybandhu.agent.repository.AgentRepository;
import paybandhu.notification.SmsService;
import paybandhu.security.domain.Role;
import paybandhu.security.domain.User;
import paybandhu.security.repository.UserRepository;
import paybandhu.security.service.TemporaryPasswordService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentApplicationServiceImp implements AgentApplicationService{

    private final AgentApplicationRepository agentApplicationRepository;
    private final AgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SmsService smsService;
    private final TemporaryPasswordService temporaryPasswordService;

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
    @Transactional
    public void activateAgent(Long applicationId) {

        AgentApplication application =
                agentApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Agent application not found: " + applicationId
                                ));

        if(application.getStatus() != AgentApplicationStatus.ACTIVATION_PENDING){
            throw new IllegalStateException(
                    "Agent can only be activated when application" +
                            "is ACTIVATION_PENDING"
            );
        }

        //prevent duplicate user account
        if(userRepository.findByMobileNumber(application.getMobileNumber()).isPresent()){
            throw new IllegalStateException(
                    "user already exists with mobile number: "
                    + application.getMobileNumber()
            );
        }

        //create agent
        Agent agent = Agent.builder()
                .agentCode(generateAgentCode())
                .panNumber(application.getPanNumber())
                .firstName(application.getFirstName())
                .middleName(application.getMiddleName())
                .lastName(application.getLastName())
                .mobileNumber(application.getMobileNumber())
                .emailAddress(application.getEmailAddress())
                .aadhaarNumber(application.getAadhaarNumber())
                .address(application.getAddress())
                .status(AgentStatus.ACTIVE)
                .dateOfBirth(application.getDateOfBirth())
                .gender(application.getGender())
                .registrationIp(application.getRegistrationIp())
                .build();

        Agent savedAgent = agentRepository.save(agent);

        String temporaryPassword =
                temporaryPasswordService.generate();

        User user = User.builder()
                .mobileNumber(application.getMobileNumber())
                .password(passwordEncoder.encode(temporaryPassword))
                .role(Role.AGENT)
                .enabled(true)
                .mustChangePassword(true)
                .agent(savedAgent)
                .build();

        userRepository.save(user);

        String message =
                "Your PayBandhu Agent account has been activated. "
                        + "Temporary password: "
                        + temporaryPassword
                        + ". Please change your password after login.";

        smsService.send(
                application.getMobileNumber(),
                message
        );

        application.setStatus(
                AgentApplicationStatus.ACTIVATED
        );

        agentApplicationRepository.save(application);

    }

    private String generateAgentCode() {

        return "AGT-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
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
