package paybandhu.retailer.api.response;

import lombok.*;
import paybandhu.retailer.domain.RetailerStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetailerVerificationResponse {

    private Long id;

    private String retailerCode;

    private RetailerStatus status;

    private String message;
}
