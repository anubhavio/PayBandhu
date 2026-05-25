package paybandhu.agent.api;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRegistrationRequest {

    @NotBlank(message = "Pin code is required ")
    private String pinCode;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "City is required")
    private String city;

    private String streetAddress;
}
