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
