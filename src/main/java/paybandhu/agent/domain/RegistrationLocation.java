package paybandhu.agent.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationLocation {

    @Column(name = "registration_latitude", precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "registration_longitude", precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "registration_accuracy_meters")
    private BigDecimal accuracyMeters;

    @Column(name = "registration_location_captured_at")
    private LocalDateTime capturedAt;
}
