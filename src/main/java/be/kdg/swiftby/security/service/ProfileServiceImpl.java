package be.kdg.swiftby.security.service;

import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.security.ProfileDto;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import be.kdg.swiftby.domain.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileServiceInt {
    private final TechnicianRepository technicianRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileServiceImpl(TechnicianRepository technicianRepository, PasswordEncoder passwordEncoder) {
        this.technicianRepository = technicianRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerNewUserAccount(ProfileDto technician) throws UserAlreadyExistsException {
        if (technicianRepository.findByEmail(technician.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("There is an account with that email: " + technician.getUsername());
        }

        Technician user = new Technician();
        user.setFirstName(technician.getFirstName());
        user.setLastName(technician.getLastName());
        user.setPassword(passwordEncoder.encode(technician.getPassword()));
        user.setEmail(technician.getUsername());

        return technicianRepository.save(user);
    }
}

