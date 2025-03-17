package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.BikeOwner;

import java.util.List;

public interface BikeOwnerService {
    List<BikeOwner> getAll();
    BikeOwner getById(Long id);
    // TODO:
    BikeOwner save(String email,
                   String password,
                   String firstName,
                   String lastName,
                   String phoneNumber);
    void remove(Long id);
}
