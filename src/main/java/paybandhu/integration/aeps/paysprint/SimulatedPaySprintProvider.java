package paybandhu.integration.aeps.paysprint;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import paybandhu.integration.aeps.AepsProvider;
import paybandhu.integration.aeps.AepsProviderRequest;
import paybandhu.integration.aeps.AepsProviderResponse;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SimulatedPaySprintProvider implements AepsProvider {
    @Override
    public AepsProviderResponse balanceEnquiry(AepsProviderRequest request) {

        return AepsProviderResponse.builder()
                .providerReference("PS-" + UUID.randomUUID())
                .status("SUCCESS")
                .balance(new BigDecimal("25000.00"))
                .message("Balance enquiry successful")
                .build();
    }
}
