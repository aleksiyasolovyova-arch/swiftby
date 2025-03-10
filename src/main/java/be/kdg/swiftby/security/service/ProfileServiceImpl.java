package be.kdg.swiftby.security.service;

import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.security.ProfileDto;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import be.kdg.swiftby.security.UserAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileServiceInt {
    private final TechnicianRepository technicianRepository;
    private PasswordEncoder passwordEncoder;

    public ProfileServiceImpl(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }


    @Override
    public User registerNewUserAccount(ProfileDto technician) {
        if (emailExists(technician.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with that email address: "
                    + technician.getUsername());
        }
        Technician user = new Technician();
        user.setFirstName(technician.getFirstName());
        user.setLastName(technician.getLastName());
        user.setPassword(passwordEncoder.encode(technician.getPassword()));
        user.setEmail(technician.getUsername());
        return technicianRepository.save(user);
    }

    private boolean emailExists(String email) {
        return technicianRepository.findByEmail(email).isPresent();
    }
}
