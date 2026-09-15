package paybandhu.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paybandhu.agent.api.response.AgentKycResponse;
import paybandhu.agent.domain.AgentApplication;
import paybandhu.agent.domain.AgentApplicationStatus;
import paybandhu.agent.domain.AgentKyc;
import paybandhu.agent.domain.AgentKycStatus;
import paybandhu.agent.repository.AgentApplicationRepository;
import paybandhu.agent.repository.AgentKycRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentKycServiceImp implements AgentKycService{

    private final AgentApplicationRepository agentApplicationRepository;
    private final AgentKycRepository agentKycRepository;

    @Override
    @Transactional
    public AgentKyc initiateKyc(Long applicationId) {

        AgentApplication application =
                agentApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException("Agent application not found " + applicationId)
                        );

        if(application.getStatus() != AgentApplicationStatus.SUBMITTED){
            throw new IllegalStateException("KYC can only be initiate for submitted application"
            );
        }

        if (agentKycRepository.existsByAgentApplicationId(applicationId)) {
            throw new IllegalStateException(
                    "KYC already exists for application: " + applicationId
            );
        }

        AgentKyc agentKyc = AgentKyc.builder()
                .kycReferenceNumber(generateKycReferenceNumber())
                .agentApplication(application)
                .status(AgentKycStatus.PENDING)
                .build();


        return   agentKycRepository.save(agentKyc);
    }

    public String generateKycReferenceNumber(){
        return "KYC-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

    @Override
    public AgentKyc getKycByApplicationId(Long applicationId) {
        return agentKycRepository.findByAgentApplicationId(applicationId)
                .orElseThrow(() ->  new IllegalArgumentException(" Kyc not found for application: " + applicationId)
                );
    }


    @Override
    public AgentKyc getKycByReferenceNumber(String kycReferenceNumber) {
        return agentKycRepository.findByKycReferenceNumber(kycReferenceNumber)
                .orElseThrow(() -> new IllegalArgumentException("Kyc not found for reference number: " + kycReferenceNumber));
    }

    @Override
    public AgentKyc completeKyc(Long applicationId) {
        return null;
    }

    @Override
    public AgentKyc verifyKyc(Long applicationId) {
        return null;
    }

    @Override
    public AgentKyc rejectKyc(Long applicationId, String rejectionReason) {
        return null;
    }

    @Override
    public AgentKyc failKyc(Long applicationId, String failureReason) {
        return null;
    }

    @Override
    public List<AgentKyc> getKycByStatus(AgentKycStatus status) {
        return List.of();
    }

    private AgentKycResponse mapToResponse(AgentKyc kyc) {

        return AgentKycResponse.builder()
                .id(kyc.getId())
                .kycReferenceNumber(kyc.getKycReferenceNumber())
                .applicationId(kyc.getAgentApplication().getId())
                .status(kyc.getStatus())
                .failureReason(kyc.getFailureReason())
                .rejectionReason(kyc.getRejectionReason())
                .createdAt(kyc.getCreatedAt())
                .verifiedAt(kyc.getVerifiedAt())
                .build();
    }
}
