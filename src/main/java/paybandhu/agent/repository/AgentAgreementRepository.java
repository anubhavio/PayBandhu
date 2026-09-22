package paybandhu.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paybandhu.agent.domain.AgentAgreement;

import java.util.Optional;

public interface AgentAgreementRepository extends JpaRepository<AgentAgreement, Long> {

    Optional<AgentAgreement> findByAgreementReferenceNumber(
            String agreementReferenceNumber
    );

    Optional<AgentAgreement> findByAgentApplicationId(
            Long applicationId
    );

    boolean existsByAgentApplicationId(
            Long applicationId
    );
}
