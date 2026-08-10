package paybandhu.retailer.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.api.request.AddressRegistrationRequest;
import paybandhu.common.validation.ValidAadhaar;
import paybandhu.common.validation.ValidMobileNumber;
import paybandhu.common.validation.ValidPan;

@Getter
@Setter
public class RetailerRegistrationRequest {

    @NotBlank(message = "FirstName is required")
    private String firstName;

    private String middleName;

    @NotBlank(message = "LastName is required")
    private String lastName;

    @ValidMobileNumber
    private String mobileNumber;

    @Email(message = "Invalid Email Address")
    private String emailAddress;

    @ValidPan
    private String panNumber;

    @ValidAadhaar
    private String aadhaarNumber;

    @NotBlank
    @Valid
    private AddressRegistrationRequest address;

}
