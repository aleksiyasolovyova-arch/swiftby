package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.SuperAdmin;

import java.util.List;

public interface SuperAdminService {
    List<SuperAdmin> getAll();

    SuperAdmin getById(Long id);

    SuperAdmin save(String email, String password, String firstName, String lastName, String phoneNumber);

    void remove(Long id);
}
