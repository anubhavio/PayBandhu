package paybandhu.retailer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paybandhu.retailer.domain.Retailer;

import java.util.Optional;

public interface RetailerRepository extends JpaRepository<Retailer, Long> {
    Optional<Retailer> findByRetailerCode(String retailerCode);
    Optional<Retailer> findByMobileNumber(String mobileNumber);
    Optional<Retailer> findByPanNumber(String panNumber);
    Optional<Retailer> findByAadhaarNumber(String aadhaarNumber);
}
