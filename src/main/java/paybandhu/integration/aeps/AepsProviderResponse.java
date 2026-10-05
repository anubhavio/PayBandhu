package paybandhu.integration.aeps;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AepsProviderResponse {

    private String providerReference;

    private String status;

    private BigDecimal balance;

    private String message;
}
