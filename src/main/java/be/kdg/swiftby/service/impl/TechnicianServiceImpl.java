package be.kdg.swiftby.service.impl;

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

    FacilityMapper facilityMapper;

    Logger log = LoggerFactory.getLogger(TechnicianService.class);

    public TechnicianServiceImpl(TechnicianRepository technicianRepository, AdministratorRepository administratorRepository, FacilityRepository facilityRepository, FacilityMapper facilityMapper) {
        this.technicianRepository = technicianRepository;
        this.administratorRepository = administratorRepository;
        this.facilityRepository = facilityRepository;
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
    public Technician saveTechnician(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber) {
        return technicianRepository.save(new Technician(facility, email, password, firstName, lastName, phoneNumber));
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
        technicianRepository.deleteAllByFacilityId(id);
    }
}
