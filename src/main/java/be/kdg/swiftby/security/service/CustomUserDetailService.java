package be.kdg.swiftby.security.service;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.repository.testEnvironment.SuperAdminRepository;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import be.kdg.swiftby.security.CustomUserDetails;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    private final SuperAdminRepository superAdminRepository;
    private final TechnicianRepository technicianRepository;
    private final AdministratorRepository administratorRepository;
    private final BikeOwnerRepository bikeOwnerRepository;

    public CustomUserDetailService(SuperAdminRepository superAdminRepository, TechnicianRepository technicianRepository, AdministratorRepository administratorRepository, BikeOwnerRepository bikeOwnerRepository) {
        this.superAdminRepository = superAdminRepository;
        this.technicianRepository = technicianRepository;
        this.administratorRepository = administratorRepository;
        this.bikeOwnerRepository = bikeOwnerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return technicianRepository.findByEmail(username)
                .map(user -> buildUserDetails(user, "TECHNICIAN"))
                .or(() -> administratorRepository.findAdministratorByEmail(username)
                        .map(user -> buildUserDetails(user, "ADMINISTRATOR")))
                .or(() -> superAdminRepository.findByEmail(username)
                        .map(user -> buildUserDetails(user, "SUPERADMIN")))
                .or(() -> bikeOwnerRepository.findByEmail(username)
                        .map(user -> buildUserDetails(user, "BIKEOWNER")))
                .orElseThrow(() -> NotFoundException.forUserWithEmail(username));
    }


    private UserDetails buildUserDetails(User user, String role) {
        return new
                CustomUserDetails(
                user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("ROLE_"+role)
        );
    }

}
