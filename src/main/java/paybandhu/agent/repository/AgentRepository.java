package paybandhu.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paybandhu.agent.domain.Agent;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {

    Optional<Agent> findByAgentCode(String agentCode);
    Optional<Agent> findByMobileNumber(String mobileNumber);
    Optional<Agent> findByPanNumber(String panNumber);
    Optional<Agent> findByAadhaarNumber(String aadhaarNumber);
}
