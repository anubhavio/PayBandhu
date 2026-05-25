package paybandhu.agent.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import paybandhu.common.validation.ValidAadhaar;
import paybandhu.common.validation.ValidMobileNumber;
import paybandhu.common.validation.ValidPan;

import java.time.LocalDate;

@Getter
@Setter
public class AgentRegistrationRequest {

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

    @JsonFormat(pattern = "dd-mm-yyyy")
    private LocalDate dataOfBirth;

    @Valid
    @NotBlank(message = "Address is required")
    private AddressRegistrationRequest address;


}
