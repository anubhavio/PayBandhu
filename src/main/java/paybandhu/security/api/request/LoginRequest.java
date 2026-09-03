package paybandhu.security.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import paybandhu.common.validation.ValidMobileNumber;

@Getter
@Setter
public class LoginRequest {

    @NotBlank
    @ValidMobileNumber
    private String mobileNumber;

    @NotBlank
    private String password;
}
