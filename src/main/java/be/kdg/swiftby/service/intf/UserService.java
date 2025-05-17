package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.User;

public interface UserService {
    User getUserByEmail(String email);
}
