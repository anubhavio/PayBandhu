package paybandhu.retailer.api.response;

import lombok.*;
import paybandhu.retailer.domain.RetailerStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetailerResponse {

    private Long id;

    private String firstName;

    private String retailerCode;

    private String mobileNumber;

    private RetailerStatus status;

    private String message;
}
