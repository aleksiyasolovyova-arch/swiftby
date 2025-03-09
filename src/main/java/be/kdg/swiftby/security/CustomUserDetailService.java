package be.kdg.swiftby.security;

import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {
   //change so it's not hardcoded technician
    private PasswordEncoder encoder;

     private final TechnicianRepository technicianRepository;
     public CustomUserDetailService(TechnicianRepository technicianRepository) {
         this.technicianRepository = technicianRepository;
     }


     @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException{
         return technicianRepository
                 .findByEmail(username)
                 .map(technician -> new CustomUserDetails(
                         technician.getEmail(),
                         technician.getPassword(),
                         AuthorityUtils.createAuthorityList("ROLE_" + technician.getClass().getSimpleName().toUpperCase())
                 ))
                 .orElseThrow(() -> new UsernameNotFoundException(username));
     }
}
