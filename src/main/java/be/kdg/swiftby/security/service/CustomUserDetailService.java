package be.kdg.swiftby.security.service;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
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
    private final TechnicianRepository technicianRepository;
    private final AdministratorRepository administratorRepository;

    public CustomUserDetailService(TechnicianRepository technicianRepository, AdministratorRepository administratorRepository) {
        this.technicianRepository = technicianRepository;
        this.administratorRepository = administratorRepository;
    }


    @Override
    public UserDetails loadUserByUsername(final String username) throws AlreadyExistsException {
        return technicianRepository.findByEmail(username)
                .map(user -> buildUserDetails(user, "TECHNICIAN"))
                .orElseGet(() -> administratorRepository.findAdministratorByEmail(username)
                        .map(user -> buildUserDetails(user, "ADMIN"))
                .orElseThrow(() -> NotFoundException.forUserWithEmail(username)));
    }

    private UserDetails buildUserDetails(User user, String role) {
        String email;
        String password;

        if (user instanceof Technician technician) {
            email = technician.getEmail();
            password = technician.getPassword();
        } else if (user instanceof Administrator administrator) {
            email = administrator.getEmail();
            password = administrator.getPassword();
        } else {
            throw new IllegalArgumentException("Unsupported user type");
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
