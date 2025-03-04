package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.Administrator;

import java.util.List;

public interface AdministratorService {
    List<Administrator> getAllAdministrators();
    Administrator getAdministratorById(Long id);
    Administrator saveAdministrator(String email, String password, String firstName, String lastName, String phoneNumber);
    void removeAdministrator(Long id);
    void removeAllByFacilityId(Long id);

}
