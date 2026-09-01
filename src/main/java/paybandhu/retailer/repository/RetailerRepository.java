package paybandhu.retailer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paybandhu.retailer.domain.Retailer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

public interface RetailerRepository extends JpaRepository<Retailer, Long> {
    Optional<Retailer> findByRetailerCode(String retailerCode);
    Optional<Retailer> findByMobileNumber(String mobileNumber);
    Optional<Retailer> findByPanNumber(String panNumber);
    Optional<Retailer> findByAadhaarNumber(String aadhaarNumber);

    boolean existsByRetailerCode(String retailerCode);

    boolean existsByMobileNumber(String mobileNumber);

    boolean existsByAadhaarNumber(String aadhaarNumber);

    boolean existsByPanNumber(String panNumber);

    Page<Retailer> findByAgentId(Long agentId, Pageable pageable);
}
