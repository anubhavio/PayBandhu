package paybandhu.integration.aeps;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class AepsProviderRequest {

    private String transactionReference;
    private String aadhaarNumber;
    private String bankCode;
}
