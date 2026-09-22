package paybandhu.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paybandhu.agent.api.response.AgentKycResponse;
import paybandhu.agent.domain.*;
import paybandhu.agent.repository.AgentApplicationRepository;
import paybandhu.agent.repository.AgentKycDocumentRepository;
import paybandhu.agent.repository.AgentKycRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentKycServiceImp implements AgentKycService{

    private static final Set<KycDocumentType> REQUIRED_DOCUMENTS =
            Set.of(
                    KycDocumentType.AADHAAR,
                    KycDocumentType.PAN,
                    KycDocumentType.SIGNATURE
            );

    private final AgentApplicationRepository agentApplicationRepository;
    private final AgentKycRepository agentKycRepository;
    private final AgentKycDocumentRepository agentKycDocumentRepository;

    @Override
    @Transactional
    public AgentKycResponse initiateKyc(Long applicationId) {

        AgentApplication application =
                agentApplicationRepository.findById(applicationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Agent application not found " + applicationId)
                        );

        if(application.getStatus() != AgentApplicationStatus.SUBMITTED){
            throw new IllegalStateException(
                    "KYC can only be initiate for submitted application"
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

        application.setStatus(AgentApplicationStatus.KYC_PENDING);
        AgentKyc saved =   agentKycRepository.save(agentKyc);

        return mapToResponse(saved);
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
                .orElseThrow(() ->  new IllegalArgumentException(
                        "Kyc not found for application: " + applicationId)
                );
    }


    @Override
    public AgentKyc getKycByReferenceNumber(String kycReferenceNumber) {
        return agentKycRepository.findByKycReferenceNumber(kycReferenceNumber)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Kyc not found for reference number: " + kycReferenceNumber));
    }

    @Override
    @Transactional
    public AgentKycResponse startKyc(Long applicationId){

        AgentKyc agentKyc = agentKycRepository
                .findByAgentApplicationId(applicationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Kyc not found for application: "+applicationId)
                );

        if(agentKyc.getStatus() != AgentKycStatus.PENDING){
            throw new IllegalStateException(
                    "Kyc can only be stated when it's status is PENDING"
            );
        }

        agentKyc.setStatus(AgentKycStatus.IN_PROGRESS);

        AgentKyc saved =  agentKycRepository.save(agentKyc);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public AgentKycResponse completeKyc(Long applicationId) {

        AgentKyc agentKyc = agentKycRepository
                .findByAgentApplicationId(applicationId).orElseThrow(() -> new IllegalArgumentException("Kyc not found for application: "+applicationId)
                );

        if(agentKyc.getStatus() != AgentKycStatus.IN_PROGRESS){
            throw new IllegalStateException(
                    "Kyc can only be completed when it is in progress"
            );
        }

        //get all documents
        List<AgentKycDocument> documents =
                agentKycDocumentRepository.findByAgentKycId(agentKyc.getId());

        // 4. Check required documents
        for (KycDocumentType requiredType : REQUIRED_DOCUMENTS) {

            AgentKycDocument document = documents.stream()
                    .filter(doc ->
                            doc.getDocumentType() == requiredType
                    )
                    .findFirst()
                    .orElseThrow(() ->
                            new IllegalStateException(
                                    "Required KYC document is missing: "
                                            + requiredType
                            )
                    );


            // Every required document must be VERIFIED
            if (document.getStatus() != KycDocumentStatus.VERIFIED) {
                throw new IllegalStateException(
                        "KYC document is not verified: "
                                + requiredType
                );

            }
        }
        /*
         * TODO:
         * Validate that all required KYC components are completed:
         *
         * Aadhaar      ✓
         * PAN          ✓
         * Signature    ✓
         * Bank Account ✓
         * Live Photo   ✓
         * Biometric    ✓
         */

        agentKyc.setStatus(AgentKycStatus.SUBMITTED);
        agentKyc.setCompletedAt(LocalDateTime.now());

        AgentKyc saved = agentKycRepository.save(agentKyc);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public AgentKycResponse verifyKyc(Long applicationId) {

        AgentKyc agentKyc = agentKycRepository.findByAgentApplicationId(applicationId)
                .orElseThrow( () -> new IllegalArgumentException(
                        "KYC not found for application: "+applicationId )
                );

        if(agentKyc.getStatus() != AgentKycStatus.SUBMITTED){
            throw new IllegalStateException(
                    "KYC can be only verified when it is SUBMITTED"
            );
        }

        AgentApplication application = agentKyc.getAgentApplication();

        if(application.getStatus() != AgentApplicationStatus.KYC_PENDING){
            throw new IllegalStateException(
                    "Application must be in KYC_PENDING state"
            );
        }

        agentKyc.setStatus(AgentKycStatus.VERIFIED);
        agentKyc.setVerifiedAt(LocalDateTime.now());

        application.setStatus(AgentApplicationStatus.KYC_COMPLETED);

        agentKycRepository.save(agentKyc);
        agentApplicationRepository.save(application);

        return mapToResponse(agentKyc);
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
