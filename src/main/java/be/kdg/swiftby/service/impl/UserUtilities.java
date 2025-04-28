package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.testEnv.Employee;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.repository.testEnvironment.SuperAdminRepository;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserUtilities {

    private final AdministratorRepository administratorRepository;
    private final  TechnicianRepository technicianRepository;
    private final  SuperAdminRepository superAdminRepository;
    private final  BikeOwnerRepository bikeOwnerRepository;

    public UserUtilities(AdministratorRepository administratorRepository, TechnicianRepository technicianRepository, SuperAdminRepository superAdminRepository, BikeOwnerRepository bikeOwnerRepository) {
        this.administratorRepository = administratorRepository;
        this.technicianRepository = technicianRepository;
        this.superAdminRepository = superAdminRepository;
        this.bikeOwnerRepository = bikeOwnerRepository;
    }

    public boolean isExistingUser(String email) {
        //if the email is already in use in any user repository, return true. otherwise, return false
        return superAdminRepository.existsByEmail(email)
                || administratorRepository.existsByEmail(email)
                || technicianRepository.existsByEmail(email)
                || bikeOwnerRepository.existsByEmail(email);

    }

}
