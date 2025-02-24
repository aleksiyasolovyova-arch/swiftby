package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.Administrator;

import java.util.List;

public interface EmployeeService {
    List<Administrator> getAll();
    Administrator getById(Long id);
    Administrator save(String email, String password, String firstName, String lastName, String phoneNumber);
    void remove(Long id);
}
