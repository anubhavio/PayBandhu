package paybandhu.security.service;

import paybandhu.security.api.request.LoginRequest;
import paybandhu.security.api.response.LoginResponse;

public interface AuthenticationService {

    LoginResponse login(LoginRequest request);
}
