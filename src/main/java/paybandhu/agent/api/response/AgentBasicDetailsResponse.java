package paybandhu.agent.api.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentBasicDetailsResponse {

    private String firstName;

    private String middleName;

    private String lastName;

    private String mobileNumber;

    private String emailAddress;

    private LocalDate dateOfBirth;
}
