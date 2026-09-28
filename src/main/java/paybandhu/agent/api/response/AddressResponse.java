package paybandhu.agent.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private String pinCode;

    private String state;

    private String city;

    private String streetAddress;

}
