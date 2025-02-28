package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.service.intf.AdministratorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdministratorServiceImpl implements AdministratorService {
    @Override
    public List<Administrator> getAllAdministrators() {
        return null;
    }

    @Override
    public Administrator getAdministratorById(Long id) {
        return null;
    }

    @Override
    public Administrator saveAdministrator(String email, String password, String firstName, String lastName, String phoneNumber) {
        return null;
    }

    @Override
    public void removeAdministrator(Long id) {

    }

    @Override
    public void removeAllByFacilityId(Long id) {

    }
}
