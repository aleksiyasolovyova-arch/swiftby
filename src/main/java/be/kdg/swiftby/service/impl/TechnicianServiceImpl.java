package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
import be.kdg.swiftby.repository.testEnvironment.FacilityRepository;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import be.kdg.swiftby.service.dto.mapper.FacilityMapper;
import be.kdg.swiftby.service.intf.TechnicianService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class TechnicianServiceImpl implements TechnicianService {
    TechnicianRepository technicianRepository;
    AdministratorRepository administratorRepository;
    FacilityRepository facilityRepository;
    UserUtilities userUtilities;

    FacilityMapper facilityMapper;

    Logger log = LoggerFactory.getLogger(TechnicianService.class);

    public TechnicianServiceImpl(TechnicianRepository technicianRepository,
                                 AdministratorRepository administratorRepository,
                                 FacilityRepository facilityRepository,
                                 UserUtilities userUtilities,
                                 FacilityMapper facilityMapper) {

        this.technicianRepository = technicianRepository;
        this.administratorRepository = administratorRepository;
        this.facilityRepository = facilityRepository;
        this.userUtilities = userUtilities;
        this.facilityMapper = facilityMapper;
    }

    @Override
    public List<Technician> getAllTechnicians() {
        return technicianRepository.findAll();
    }

    @Override
    public Technician getTechnicianById(Long id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));
    }


    @Override
    public Technician saveTechnician(Facility facility,
                                     String email,
                                     String password,
                                     String firstName,
                                     String lastName,
                                     String phoneNumber) {
        //If there already exists a user with that email, throw an exception
        if (userUtilities.isExistingUser(email)) {
            throw AlreadyExistsException.forUserWithEmail(email);
        }

        return technicianRepository.save(
                        new Technician(facility, email, password, firstName, lastName, phoneNumber)
                );
    }


    @Override
    public void removeTechnician(Long id) {
        technicianRepository.deleteById(id);
    }

    @Override
    public List<Technician> getAllByFacilityId(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> NotFoundException.forFacility(facilityId));
        return technicianRepository.findAllByFacility(facility);
    }

    @Override
    public Technician getByFacilityIdAndTechnicianId(Long facilityId, Long technicianId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> NotFoundException.forFacility(facilityId));
        Technician technician = technicianRepository.findByFacilityAndId(facility, technicianId)
                .orElseThrow(() -> NotFoundException.forTechnician(technicianId));
        log.debug("Found technician with id {} in facility with id {}: {}", technicianId, facilityId, technician);
        return technician;
    }

    @Override
    public void removeAllByFacilityId(Long id) {
        if (!facilityRepository.existsById(id)) {
            throw NotFoundException.forFacility(id);
        }
        administratorRepository.deleteAllByFacilityId(id);
        log.debug("Removed all technicians in facility with id {}", id);
    }
    @Override
    public List<Technician> getAllUnapproved() {
        return technicianRepository.findAllUnapproved();
    }
    @Override
    public void approve(Long technicianId) {
        Technician technician = technicianRepository.findById(technicianId)
                .orElseThrow(() -> NotFoundException.forTechnician(technicianId));
        technician.setApproved(true);
        technicianRepository.save(technician);
    }

}
