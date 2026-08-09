package paybandhu.agent.domain;

import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
public class RegistrationLocation {

    private BigDecimal latitude;

    private BigDecimal longitude;

    private BigDecimal accuracyMeters;

    private LocalDateTime capturedAt;
}
