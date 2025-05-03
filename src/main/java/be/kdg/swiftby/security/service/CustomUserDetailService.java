package be.kdg.swiftby.security.service;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
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

    public CustomUserDetailService(SuperAdminRepository superAdminRepository, TechnicianRepository technicianRepository, AdministratorRepository administratorRepository) {
        this.superAdminRepository = superAdminRepository;
        this.technicianRepository = technicianRepository;
        this.administratorRepository = administratorRepository;
    }


    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        return technicianRepository.findByEmail(username)
                .map(user -> buildUserDetails(user, "TECHNICIAN"))
                .or(() -> administratorRepository.findAdministratorByEmail(username)
                        .map(user -> buildUserDetails(user, "ADMIN")))
                .or(() -> superAdminRepository.findByEmail(username)
                        .map(user -> buildUserDetails(user, "SUPERADMIN")))
                .orElseThrow(() -> NotFoundException.forUserWithEmail(username));
    }


    private UserDetails buildUserDetails(User user, String role) {
        String email;
        String password;

        switch (user) {
            case Technician technician -> {
                email = technician.getEmail();
                password = technician.getPassword();
            }
            case Administrator administrator -> {
                email = administrator.getEmail();
                password = administrator.getPassword();
            }
            case SuperAdmin superadmin -> {
                email = superadmin.getEmail();
                password = superadmin.getPassword();
            }
            case null, default -> throw new IllegalArgumentException("Unsupported user type");
        }
        return new

                CustomUserDetails(
                email,
                password,
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList("ROLE_"+role)
        );
    }

}
