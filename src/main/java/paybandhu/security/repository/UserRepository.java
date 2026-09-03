package paybandhu.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import paybandhu.security.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByMobileNumber(String mobileNumber);
}
