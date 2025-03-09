package be.kdg.swiftby.security;

import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.presentation.viewModel.TechnicianViewModel;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    private final TechnicianRepository technicianRepository;
    private PasswordEncoder passwordEncoder;

    public ProfileService(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public User registerUser(TechnicianViewModel technician) throws Exception {
        if (technician.getUsername() != null) {
            throw new Exception("Username is already in use");
        }
        Technician user = new Technician();
        user.setFirstName(technician.getFirstName());
        user.setLastName(technician.getLastName());
        user.setPassword(passwordEncoder.encode(technician.getPassword()));
        user.setPhoneNumber(technician.getPhoneNumber());
        return technicianRepository.save(user);
    }
}
