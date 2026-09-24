package paybandhu.security.service;

import paybandhu.security.domain.User;

public interface CurrentUserService {

    Long getCurrentAgentId();

    User getCurrentUser();

}
