package paybandhu.retailer.api.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.api.request.AddressRegistrationRequest;

@Getter
@Setter
public class RetailerUpdateRequest {
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @Size(max = 50)
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^[0-9]{10}$")
    private String mobileNumber;

    private AddressRegistrationRequest address;

}
