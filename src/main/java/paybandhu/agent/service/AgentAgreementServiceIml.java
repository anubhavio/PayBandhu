package paybandhu.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paybandhu.agent.api.response.AgentAgreementResponse;
import paybandhu.agent.domain.AgentAgreement;
import paybandhu.agent.domain.AgentApplication;
import paybandhu.agent.domain.AgentApplicationStatus;
import paybandhu.agent.domain.AgreementStatus;
import paybandhu.agent.repository.AgentAgreementRepository;
import paybandhu.agent.repository.AgentApplicationRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AgentAgreementServiceIml implements AgentAgreementService{

    private final AgentAgreementRepository agentAgreementRepository;
    private final AgentApplicationRepository agentApplicationRepository;

    @Override
    @Transactional
    public AgentAgreementResponse initiateAgreement(Long applicationId) {

        AgentApplication application = agentApplicationRepository.findById(applicationId)
                .orElseThrow(()-> new IllegalArgumentException(
                        "Agent application not found: "+applicationId)
                );

        if (application.getStatus() != AgentApplicationStatus.AGREEMENT_PENDING) {
            throw new IllegalStateException(
                    "Agreement can only be initiated for an application " +
                            "with AGREEMENT_PENDING status"
            );
        }

        if (agentAgreementRepository.existsByAgentApplicationId(applicationId)) {
            throw new IllegalStateException(
                    "Agreement already exists for application: " + applicationId
            );
        }

        AgentAgreement agentAgreement = AgentAgreement.builder()
                .agreementReferenceNumber(generateAgreementReferenceNumber())
                .agentApplication(application)
                .agreementVersion("1.0")
                .status(AgreementStatus.PENDING)
                .build();

        AgentAgreement saved =
                agentAgreementRepository.save(agentAgreement);

        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public AgentAgreementResponse acceptAgreement(Long applicationId) {

        AgentAgreement agreement =
                agentAgreementRepository.findByAgentApplicationId(applicationId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Agreement not found for application: "
                                                + applicationId
                                ));

        if (agreement.getStatus() != AgreementStatus.PENDING) {
            throw new IllegalStateException(
                    "Agreement can only be accepted when it is PENDING"
            );
        }

        AgentApplication application =
                agreement.getAgentApplication();

        if (application.getStatus()
                != AgentApplicationStatus.AGREEMENT_PENDING) {

            throw new IllegalStateException(
                    "Application must be in AGREEMENT_PENDING state"
            );
        }

        agreement.setStatus(AgreementStatus.ACCEPTED);
        agreement.setAcceptedAt(LocalDateTime.now());

        application.setStatus(
                AgentApplicationStatus.AGREEMENT_ACCEPTED
        );

        agentAgreementRepository.save(agreement);
        agentApplicationRepository.save(application);

        return mapToResponse(agreement);
    }

    private String generateAgreementReferenceNumber() {
        return "AGR-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }

    private AgentAgreementResponse mapToResponse(
            AgentAgreement agreement) {

        return AgentAgreementResponse.builder()
                .id(agreement.getId())
                .agreementReferenceNumber(agreement.getAgreementReferenceNumber())
                .applicationId(agreement.getAgentApplication().getId())
                .agreementVersion(agreement.getAgreementVersion())
                .status(agreement.getStatus())
                .agreementFileReference(agreement.getAgreementFileReference())
                .createdAt(agreement.getCreatedAt())
                .acceptedAt(agreement.getAcceptedAt())
                .rejectedAt(agreement.getRejectedAt())
                .rejectionReason(agreement.getRejectionReason())
                .build();
    }
}
