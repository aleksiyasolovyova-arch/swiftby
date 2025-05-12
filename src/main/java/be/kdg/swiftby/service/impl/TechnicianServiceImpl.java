package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.Technician;
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
import java.util.Optional;

@Service
@Transactional
public class TechnicianServiceImpl implements TechnicianService {
    private TechnicianRepository technicianRepository;
    private AdministratorRepository administratorRepository;
    private FacilityRepository facilityRepository;
    private UserUtilities userUtilities;

    private FacilityMapper facilityMapper;

    private Logger log = LoggerFactory.getLogger(TechnicianService.class);

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
    public List<Technician> getAll() {
        return technicianRepository.findAll();
    }

    @Override
    public Technician getById(Long id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forTechnician(id));
    }


    @Override
    public Technician create(Long facilityId,
                             String email,
                             String password,
                             String firstName,
                             String lastName,
                             String phoneNumber) {

        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> NotFoundException.forFacility(facilityId));

        //If there already exists a user with that email, throw an exception
        if (userUtilities.isExistingUser(email)) {
            throw AlreadyExistsException.forUserWithEmail(email);
        }

        Technician technician = technicianRepository.save(
                new Technician(facility, email, password, firstName, lastName, phoneNumber)
        );

        log.debug("New technician is created: {}", technician);

        return technician;
    }


    @Override
    public void remove(Long id) {
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

    @Override
    public Technician update(Long oldFacilityId, Long id, String email,
                             String password, String firstName, String lastName,
                             String phoneNumber, Long newFacilityId) {

        Facility oldFacility = facilityRepository.findById(oldFacilityId)
                .orElseThrow(() -> NotFoundException.forFacility(oldFacilityId));

        Technician technician = technicianRepository.findByFacilityAndId(oldFacility, id)
                .orElseThrow(() -> NotFoundException.forAdmin(id));

        if (email != null && userUtilities.isExistingUser(email) && !technician.getEmail().equals(email)) {
            throw AlreadyExistsException.forUserWithEmail(email);
        }

        Facility newFacility = null;

        if (newFacilityId != null) {
            newFacility = facilityRepository.findById(newFacilityId)
                    .orElseThrow(() -> NotFoundException.forFacility(newFacilityId));
        }

        technician.setEmail(email != null ? email : technician.getEmail());
        technician.setPassword(password != null ? password : technician.getPassword());
        technician.setFirstName(firstName != null ? firstName : technician.getFirstName());
        technician.setLastName(lastName != null ? lastName : technician.getLastName());
        technician.setPhoneNumber(phoneNumber != null ? phoneNumber : technician.getPhoneNumber());
        technician.setFacility(newFacilityId != null ? newFacility : technician.getFacility());

        return technician;
    }

    @Override
    public Technician getByEmail(String email) {
        return technicianRepository.findByEmail(email).get();
    }
}
