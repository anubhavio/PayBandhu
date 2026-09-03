package paybandhu.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import paybandhu.security.api.request.LoginRequest;
import paybandhu.security.api.response.LoginResponse;
import paybandhu.security.domain.User;
import paybandhu.security.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp  implements AuthenticationService{

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByMobileNumber(request
                .getMobileNumber())
                .orElseThrow(() -> new RuntimeException("Invalid mobile number of password")
                );

        if(!user.isEnabled()){
            throw new RuntimeException("user account is disabled");
        }

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid mobile number of password");
        }

        return LoginResponse.builder()
                .userId(user.getId())
                .mobileNumber(user.getMobileNumber())
                .role(user.getRole().name())
                .build();


    }
}
