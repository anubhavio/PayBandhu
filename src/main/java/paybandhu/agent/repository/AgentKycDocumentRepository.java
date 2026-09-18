package paybandhu.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paybandhu.agent.domain.AgentKycDocument;
import paybandhu.agent.domain.KycDocumentStatus;
import paybandhu.agent.domain.KycDocumentType;

import java.util.List;
import java.util.Optional;

public interface AgentKycDocumentRepository extends JpaRepository<AgentKycDocument, Long> {

    List<AgentKycDocument> findByAgentKycId(Long agentKycId);

    Optional<AgentKycDocument> findByAgentKycIdAndDocumentType(
            Long agentKycId,
            KycDocumentType documentType
    );

    boolean existsByAgentKycIdAndDocumentType(
            Long agentKycId,
            KycDocumentType documentType
    );

    List<AgentKycDocument> findByAgentKycIdAndStatus(
            Long agentKycId,
            KycDocumentStatus status
    );

}
