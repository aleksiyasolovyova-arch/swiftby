package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.repository.testEnvironment.SuperAdminRepository;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import be.kdg.swiftby.service.intf.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private TechnicianRepository technicianRepository;
    private AdministratorRepository administratorRepository;
    private BikeOwnerRepository bikeOwnerRepository;
    private UserUtilities userUtilities;
    private SuperAdminRepository superAdminRepository;

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        if (!userUtilities.isExistingUser(email)) {
            throw NotFoundException.forUserWithEmail(email);
        } else if (technicianRepository.existsByEmail(email)) {
            return technicianRepository.findByEmail(email).orElseThrow();
        } else if (administratorRepository.existsByEmail(email)) {
            return administratorRepository.findAdministratorByEmail(email).orElseThrow();
        } else if (bikeOwnerRepository.existsByEmail(email)) {
            return bikeOwnerRepository.findByEmail(email).orElseThrow();
        } else if (superAdminRepository.existsByEmail(email)) {
            return superAdminRepository.findByEmail(email).orElseThrow();
        } else {
            throw NotFoundException.forUserWithEmail(email);
        }
    }


}
