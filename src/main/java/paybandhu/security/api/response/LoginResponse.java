package paybandhu.security.api.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class LoginResponse {

    private Long userId;

    private String mobileNumber;

    private String role;
}
