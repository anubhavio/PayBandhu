package paybandhu.transaction.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AepsBalanceEnquiryRequest {

    @NotBlank
    private String aadhaarNumber;

    @NotBlank
    private String bankCode;
}
