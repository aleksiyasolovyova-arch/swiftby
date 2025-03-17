package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BikeOwnerServiceImpl implements BikeOwnerService {
    @Override
    public List<BikeOwner> getAll() {
        return List.of();
    }

    @Override
    public BikeOwner getById(Long id) {
        return null;
    }

    @Override
    public BikeOwner save(String email, String password, String firstName, String lastName, String phoneNumber) {
        return null;
    }

    @Override
    public void remove(Long id) {

    }
}
