package paybandhu.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import paybandhu.security.domain.User;
import paybandhu.security.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImp implements CurrentUserService {

    private final UserRepository userRepository;


    @Override
    public Long getCurrentAgentId() {



        User user = getCurrentUser();

        if (user.getAgent() == null) {
            throw new IllegalStateException(
                    "Current user is not associated with an agent"
            );
        }

        return user.getAgent().getId();


    }

    @Override
    public User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            throw new IllegalStateException("User is not authenticated");
        }

        String mobileNumber = authentication.getName();

        return userRepository
                .findByMobileNumber(mobileNumber)
                .orElseThrow(() ->
                        new IllegalStateException(
                                "Authenticated user not found"
                        ));
    }
}
