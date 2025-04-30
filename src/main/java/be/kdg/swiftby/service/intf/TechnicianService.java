package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.*;

import java.util.List;

public interface TechnicianService {

    List<Technician> getAll();
    Technician getById(Long id);
    Technician create(Long facilityId,
                      String email,
                      String password,
                      String firstName,
                      String lastName,
                      String phoneNumber);

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
