package paybandhu.agent.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import paybandhu.agent.api.request.AgentKycDocumentRequest;
import paybandhu.agent.api.response.AgentKycDocumentResponse;
import paybandhu.agent.domain.AgentKyc;
import paybandhu.agent.domain.AgentKycDocument;
import paybandhu.agent.domain.AgentKycStatus;
import paybandhu.agent.domain.KycDocumentStatus;
import paybandhu.agent.repository.AgentKycDocumentRepository;
import paybandhu.agent.repository.AgentKycRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgentKycDocumentServiceIml implements AgentKycDocumentService{

    private final AgentKycRepository agentKycRepository;
    private final AgentKycDocumentRepository agentKycDocumentRepository;

    @Override
    @Transactional
    public AgentKycDocumentResponse uploadDocument(Long applicationId, AgentKycDocumentRequest request) {

        AgentKyc agentKyc = agentKycRepository.findByAgentApplicationId(applicationId)
                .orElseThrow(() ->  new IllegalArgumentException(
                        "kyc not found for the application: "+ applicationId)
                );


        if(agentKyc.getStatus() != AgentKycStatus.IN_PROGRESS){
            throw new IllegalStateException(
                    "Document can only be uploaded when kyc is IN_PROGRESS"
            );
        }

        if(agentKycDocumentRepository
                .existsByAgentKycIdAndDocumentType(agentKyc.getId(),
                        request.getDocumentType())){
            throw new IllegalStateException(
                    "Document already exists: "
                            + request.getDocumentType()
            );
        }

        AgentKycDocument agentKycDocument = AgentKycDocument.builder()
                .agentKyc(agentKyc)
                .documentType(request.getDocumentType())
                .documentNumber(request.getDocumentNumber())
                .fileReference(request.getFileReference())
                .status(KycDocumentStatus.UPLOADED)
                .build();

        AgentKycDocument savedDoc = agentKycDocumentRepository.save(agentKycDocument);

        return mapToResponse(savedDoc);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgentKycDocumentResponse> getDocuments(Long applicationId) {

        AgentKyc agentKyc = agentKycRepository
                .findByAgentApplicationId(applicationId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "KYC not found for application: "
                                        + applicationId
                        )
                );

        return agentKycDocumentRepository
                .findByAgentKycId(agentKyc.getId())
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional
    public AgentKycDocumentResponse verifyDocument(Long applicationId, Long documentId) {

        AgentKyc agentKyc = agentKycRepository.findByAgentApplicationId(applicationId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "KYC not found for application: "+applicationId
                        )
                );

        AgentKycDocument document = agentKycDocumentRepository.findById(documentId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "KYC not found: "+documentId
                        )
                );

        if (!document.getAgentKyc().getId().equals(agentKyc.getId())) {
            throw new IllegalStateException(
                    "Document does not belong to this KYC"
            );
        }

        if (document.getStatus() != KycDocumentStatus.UPLOADED) {
            throw new IllegalStateException(
                    "Only uploaded documents can be verified"
            );
        }

        document.setStatus(KycDocumentStatus.VERIFIED);

        AgentKycDocument savedDocument =
                agentKycDocumentRepository.save(document);

        return mapToResponse(savedDocument);
    }

    @Override
    @Transactional
    public AgentKycDocumentResponse rejectDocument(Long applicationId, Long documentId, String reason) {

        AgentKyc agentKyc = agentKycRepository
                .findByAgentApplicationId(applicationId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "KYC not found for application: "
                                        + applicationId
                        )
                );

        AgentKycDocument document = agentKycDocumentRepository
                .findById(documentId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "KYC document not found: "
                                        + documentId
                        )
                );

        if (!document.getAgentKyc().getId().equals(agentKyc.getId())) {
            throw new IllegalStateException(
                    "Document does not belong to this KYC"
            );
        }

        if (document.getStatus() != KycDocumentStatus.UPLOADED) {
            throw new IllegalStateException(
                    "Only uploaded documents can be rejected"
            );
        }

        document.setStatus(KycDocumentStatus.REJECTED);

        AgentKycDocument savedDocument =
                agentKycDocumentRepository.save(document);

        return mapToResponse(savedDocument);

    }

    private AgentKycDocumentResponse mapToResponse(
            AgentKycDocument document) {

        return AgentKycDocumentResponse.builder()
                .id(document.getId())
                .kycId(document.getAgentKyc().getId())
                .documentType(document.getDocumentType())
                .status(document.getStatus())
                .fileReference(document.getFileReference())
                .build();
    }
}
