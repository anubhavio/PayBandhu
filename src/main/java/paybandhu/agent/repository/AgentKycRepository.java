package paybandhu.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import paybandhu.agent.domain.AgentApplication;
import paybandhu.agent.domain.AgentKyc;
import paybandhu.agent.domain.AgentKycStatus;

import java.util.List;
import java.util.Optional;

@Repository
interface AgentKycRepository extends JpaRepository<AgentKyc, Long> {

    Optional<AgentKyc> findByKycReferenceNumber(String kycReferenceNumber);

    Optional<AgentKyc> findByAgentApplication(AgentApplication agentApplication);

    Optional<AgentKyc> findByAgentApplicationId(Long agentApplicationId);

    boolean existsByKycReferenceNumber(String kycReferenceNumber);

    boolean existsByAgentApplication(AgentApplication agentApplication);

    boolean existsByAgentApplicationId(Long agentApplicationId);

    List<AgentKyc> findByStatus(AgentKycStatus status);
}
