package paybandhu.agent.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.domain.Gender;
import paybandhu.common.validation.ValidAadhaar;
import paybandhu.common.validation.ValidMobileNumber;
import paybandhu.common.validation.ValidPan;

import java.time.LocalDate;

@Getter
@Setter
public class AgentApplicationRequest {

    @NotBlank(message = "FirstName is required")
    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String middleName;

    @NotBlank(message = "LastName is required")
    @Size(max = 50)
    private String lastName;

    @ValidMobileNumber
    private String mobileNumber;

    @Email(message = "Invalid Email Address")
    @Size(max = 150)
    private String emailAddress;

    @ValidPan
    private String panNumber;

    @ValidAadhaar
    private String aadhaarNumber;

    private Gender gender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @NotNull
    @Valid
    private AddressRegistrationRequest address;
}
