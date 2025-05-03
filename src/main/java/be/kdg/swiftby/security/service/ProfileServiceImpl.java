package be.kdg.swiftby.security.service;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
import be.kdg.swiftby.repository.testEnvironment.FacilityRepository;
import be.kdg.swiftby.security.ProfileDto;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import be.kdg.swiftby.service.intf.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileServiceInt {
    private final FacilityRepository facilityRepository;
    private final TechnicianRepository technicianRepository;
    private final AdministratorRepository administratorRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProfileServiceImpl(FacilityRepository facilityRepository, TechnicianRepository technicianRepository, AdministratorRepository administratorRepository, PasswordEncoder passwordEncoder) {
        this.facilityRepository = facilityRepository;
        this.technicianRepository = technicianRepository;
        this.administratorRepository = administratorRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Should i make it a separate check for the email?
    @Override
    public User registerNewUserAccount(ProfileDto profile) throws AlreadyExistsException {
        Facility facility = null;
        String email = profile.getUsername();
        String role = profile.getRole();

        if(profile.getFacilityName() != null && !profile.getFacilityName().isBlank()) {
            facility = facilityRepository.findByName(profile.getFacilityName().trim())
                    .orElseThrow(() -> NotFoundException.forFacilityName("No facility found with name: " + profile.getFacilityName()));
        }

        boolean emailExists = technicianRepository.existsByEmail(email) || administratorRepository.existsByEmail(email);
        if (emailExists) {
            throw AlreadyExistsException.forUserWithEmail("There is an account with that email: " + email);
        }
        if ("TECHNICIAN".equalsIgnoreCase(role)) {
            Technician technician = new Technician();
            technician.setFirstName(profile.getFirstName());
            technician.setLastName(profile.getLastName());
            technician.setPassword(passwordEncoder.encode(profile.getPassword()));
            technician.setEmail(profile.getUsername());
            technician.setPhoneNumber(profile.getPhoneNumber());
            technician.setFacility(facility);
            return technicianRepository.save(technician);
        } else if ("ADMINISTRATOR".equalsIgnoreCase(role)) {
            Administrator admin = new Administrator();
            admin.setFirstName(profile.getFirstName());
            admin.setLastName(profile.getLastName());
            admin.setPassword(passwordEncoder.encode(profile.getPassword()));
            admin.setEmail(profile.getUsername());
            admin.setPhoneNumber(profile.getPhoneNumber());
            admin.setFacility(facility);
            return administratorRepository.save(admin);
        }
        throw new IllegalArgumentException("Invalid role selected: " + role);
    }
}

