package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.Facility;

import java.util.List;

public interface AdministratorService {
    List<Administrator> getAll();
    Administrator getById(Long id);

    Administrator getByEmail(String email);

    Administrator save(Facility facility, String email, String password, String firstName, String lastName, String phoneNumber);

    void remove(Long id);

    List<Administrator> getAllByFacilityId(Long facilityId);

    Administrator getByFacilityIdAndAdministratorId(Long facilityId, Long adminId);

    void removeAllByFacilityId(Long id);

}
