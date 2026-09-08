package paybandhu.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import paybandhu.security.api.request.LoginRequest;
import paybandhu.security.api.response.LoginResponse;
import paybandhu.security.domain.User;


@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp  implements AuthenticationService{

  private final AuthenticationManager authenticationManager;

  private final JwtService jwtService;

    @Override
    public LoginResponse login(LoginRequest request) {

       Authentication authentication = authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       request.getMobileNumber(),
                       request.getPassword()
               )
       );

       CustomUserDetails userDetails =
               (CustomUserDetails) authentication.getPrincipal();

       User user = userDetails.getUser();

       String token = jwtService.generateToken(
               user.getMobileNumber(),
               user.getRole().name()
       );

       return LoginResponse.builder()
               .userId(user.getId())
               .mobileNumber(user.getMobileNumber())
               .role(user.getRole().name())
               .token(token)
               .build();

    }
}
