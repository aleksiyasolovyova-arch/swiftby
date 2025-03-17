package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import be.kdg.swiftby.repository.testEnvironment.FacilityRepository;
import be.kdg.swiftby.repository.testEnvironment.SuperAdminRepository;
import be.kdg.swiftby.service.dto.mapper.FacilityMapper;
import be.kdg.swiftby.service.intf.SuperAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperAdminServiceImpl implements SuperAdminService {

    SuperAdminRepository superAdminRepository;
    FacilityRepository facilityRepository;
    UserUtilities userUtilities;
    FacilityMapper facilityMapper;

    public SuperAdminServiceImpl(SuperAdminRepository superAdminRepository, FacilityRepository facilityRepository, UserUtilities userUtilities, FacilityMapper facilityMapper) {
        this.superAdminRepository = superAdminRepository;
        this.facilityRepository = facilityRepository;
        this.facilityMapper = facilityMapper;
        this.userUtilities = userUtilities;
    }

    @Override
    public List<SuperAdmin> getAll() {
        return superAdminRepository.findAll();
    }

    @Override
    public SuperAdmin getById(Long id) {
        return superAdminRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forSuperAdmin(id));
    }

    //todo check if this works in superadmin class
    @Override
    public SuperAdmin save(String email, String password, String firstName, String lastName, String phoneNumber) {
        if (userUtilities.isExistingUser(email)) {
            throw AlreadyExistsException.forUserWithEmail(email);
        }

        return superAdminRepository.save(new SuperAdmin(email, password, firstName, lastName, phoneNumber));
    }

    @Override
    public void remove(Long id) {
        superAdminRepository.deleteById(id);
    }
}
