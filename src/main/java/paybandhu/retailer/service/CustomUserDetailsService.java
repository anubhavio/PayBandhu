package paybandhu.retailer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import paybandhu.security.domain.User;
import paybandhu.security.repository.UserRepository;
import paybandhu.security.service.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mobileNumber)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with mobile: " + mobileNumber
                )
                );

        return new CustomUserDetails(user);
    }
}
