package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.*;

import java.util.List;
import java.util.Optional;

public interface TechnicianService {

    List<Technician> getAll();
    Technician getById(Long id);
    Technician create(Long facilityId,
                      String email,
                      String password,
                      String firstName,
                      String lastName,
                      String phoneNumber);
    Technician getByEmail(String email);
    void remove(Long id);
    List<Technician> getAllByFacilityId(Long id);

    Technician getByFacilityIdAndTechnicianId(Long facilityId, Long technicianId);

    void removeAllByFacilityId(Long id);
    List<Technician> getAllUnapproved();

    void approve(Long technicianId);

    Technician update(Long id, Long oldFacilityId, String email,
                      String password, String firstName, String lastName,
                      String phoneNumber, Long newFacilityId);
}
