package be.kdg.swiftby.security.service;

import be.kdg.swiftby.domain.exception.AlreadyExistsException;
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
   //change eventually so it's not hardcoded technician
//    private PasswordEncoder encoder;

     private final TechnicianRepository technicianRepository;
     public CustomUserDetailService(TechnicianRepository technicianRepository) {
         this.technicianRepository = technicianRepository;
     }


     @Override
    public UserDetails loadUserByUsername(final String username) throws AlreadyExistsException {
         return technicianRepository
                 .findByEmail(username)
                 .map(technician -> new CustomUserDetails(
                         technician.getEmail(),
                         technician.getPassword(),
                         true,
                         true,
                         true,
                         true,
                         AuthorityUtils.createAuthorityList("ROLE_" + technician.getClass().getSimpleName().toUpperCase())
                 ))
                 .orElseThrow(() -> AlreadyExistsException.forUserWithEmail(username));
     }
}
