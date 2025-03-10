package be.kdg.swiftby.security.service;

import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.security.ProfileDto;

public interface ProfileServiceInt {
    User registerNewUserAccount(ProfileDto technician);
}
