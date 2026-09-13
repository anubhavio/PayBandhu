package paybandhu.agent.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import paybandhu.agent.domain.AgentApplication;
import java.util.Optional;

public interface AgentApplicationRepository extends JpaRepository<AgentApplication, Long> {

    Optional<AgentApplication> findByApplicationNumber(
            String applicationNumber
    );

    Optional<AgentApplication> findByMobileNumber(
            String mobileNumber
    );

    Optional<AgentApplication> findByAadhaarNumber(
            String aadhaarNumber
    );

    Optional<AgentApplication> findByPanNumber(
            String panNumber
    );

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByAadhaarNumber(String aadhaarNumber);

    boolean existsByPanNumber(String panNumber);
}
