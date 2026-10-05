package paybandhu.transaction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paybandhu.transaction.domain.AepsTransaction;

import java.util.Optional;

public interface AepsTransactionRepository extends JpaRepository<AepsTransaction, Long> {

    Optional<AepsTransaction> findByTransactionReference(
            String transactionReference
    );
}