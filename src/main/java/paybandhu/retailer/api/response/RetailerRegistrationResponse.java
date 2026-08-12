package paybandhu.retailer.api.response;

import lombok.*;
import paybandhu.retailer.domain.RetailerStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetailerRegistrationResponse {

    private Long id;

    private String retailerCode;

    private RetailerStatus status;

    private String message;
}
