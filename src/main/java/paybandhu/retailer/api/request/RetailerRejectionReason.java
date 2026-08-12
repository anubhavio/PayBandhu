package paybandhu.retailer.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetailerRejectionReason {

    @NotBlank
    private RetailerRejectionReason reason;
}
