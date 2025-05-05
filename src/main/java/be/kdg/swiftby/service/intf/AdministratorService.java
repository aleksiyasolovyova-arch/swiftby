package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.Facility;

import java.util.List;

public interface AdministratorService {
    List<Administrator> getAll();
    Administrator getById(Long id);

    Administrator getByEmail(String email);

    void remove(Long id);

    List<Administrator> getAllByFacilityId(Long facilityId);

    Administrator getByFacilityIdAndAdministratorId(Long facilityId, Long adminId);

    void removeAllByFacilityId(Long id);
    List<Administrator> getAllUnapproved();

    void approve(Long adminId);
    Administrator create(Long facilityId, String email,
                         String password, String firstName, String lastName,
                         String phoneNumber);

    Administrator update(Long id, Long oldFacilityId, String email,
                         String password, String firstName, String lastName,
                         String phoneNumber, Long newFacilityId);
}
