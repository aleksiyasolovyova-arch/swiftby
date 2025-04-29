package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.repository.testEnvironment.*;
import be.kdg.swiftby.service.intf.AdministratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    private final AdministratorRepository administratorRepository;
    private final UserUtilities userUtilities;

    private final  FacilityRepository facilityRepository;

    private final Logger log = LoggerFactory.getLogger(AdministratorServiceImpl.class);

    public AdministratorServiceImpl(AdministratorRepository administratorRepository,
                                    UserUtilities userUtilities,
                                    FacilityRepository facilityRepository) {

        this.administratorRepository = administratorRepository;
        this.userUtilities = userUtilities;
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<Administrator> getAll() {
        return administratorRepository.findAll();
    }

    @Override
    public Administrator getById(Long id) {
        return administratorRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forAdmin(id));
    }

    @Override
    public Administrator getByEmail(String email) {
        return administratorRepository.findAdministratorByEmail(email)
                .orElseThrow(() -> NotFoundException.forAdminEmail(email));
    }

    @Override
    public Administrator save(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber) {
        //If there already exists a user with that email, throw an exception
        if (userUtilities.isExistingUser(email)) {
            throw AlreadyExistsException.forUserWithEmail(email);
        }

        return new Administrator(facility, email, password, firstName, lastName, phoneNumber);
    }

    @Override
    public void remove(Long id) {
        if (!administratorRepository.existsById(id)) {
            throw NotFoundException.forAdmin(id);
        }
        administratorRepository.deleteById(id);
        log.debug("Removed admin with id {}", id);
    }

    @Override
    public List<Administrator> getAllByFacilityId(Long facilityId) {
        if (!facilityRepository.existsById(facilityId)) {
            throw NotFoundException.forFacility(facilityId);
        }
        return administratorRepository.findAllByFacilityId(facilityId);
    }

    @Override
    public Administrator getByFacilityIdAndAdministratorId(Long facilityId, Long adminId) {
        if (!facilityRepository.existsById(facilityId)) {
            throw NotFoundException.forFacility(facilityId);
        }
        Administrator admin = administratorRepository.findByFacilityIdAndId(facilityId, adminId)
                .orElseThrow(() -> NotFoundException.forAdmin(adminId));
        log.debug("Admin with id {} found in facility with id {}: {}", adminId, facilityId, admin);
        return admin;
    }

    @Override
    public void removeAllByFacilityId(Long id) {
        if (!facilityRepository.existsById(id)) {
            throw NotFoundException.forFacility(id);
        }
        administratorRepository.deleteAllByFacilityId(id);
        log.debug("Removed all admins in facility with id {}", id);
    }
    // In AdministratorServiceImpl.java
    @Override
    public List<Administrator> getAllUnapproved() {
        return administratorRepository.findAllUnapproved();
    }
    @Override
    public void approve(Long adminId) {
        Administrator admin = administratorRepository.findById(adminId)
                .orElseThrow(() -> NotFoundException.forAdmin(adminId));
        admin.setApproved(true);
        administratorRepository.save(admin);
    }

}
