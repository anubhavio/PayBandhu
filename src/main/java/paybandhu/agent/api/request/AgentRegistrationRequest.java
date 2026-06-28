package paybandhu.agent.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import paybandhu.agent.domain.Gender;
import paybandhu.common.validation.ValidAadhaar;
import paybandhu.common.validation.ValidMobileNumber;
import paybandhu.common.validation.ValidPan;

import java.time.LocalDate;
import java.util.List;

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

    @NotNull
    @Valid
    private AddressRegistrationRequest address;

    private Gender gender;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;

    @Valid
    @NotNull
    private List<AgentDocumentRequest> agentDocumentRequest;

}
