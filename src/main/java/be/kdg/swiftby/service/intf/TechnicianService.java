package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.*;

import java.util.List;

public interface TechnicianService {

    List<Technician> getAllTechnicians();
    Technician getTechnicianById(Long id);
    Technician saveTechnician(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber);

    void removeTechnician(Long id);
    List<Technician> getAllByFacilityId(Long id);

    Technician getByFacilityIdAndTechnicianId(Long facilityId, Long technicianId);

    void removeAllByFacilityId(Long id);
    List<Technician> getAllUnapproved();

    void approve(Long technicianId);
}
