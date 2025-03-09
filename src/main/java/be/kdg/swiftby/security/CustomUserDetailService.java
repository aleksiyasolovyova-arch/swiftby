package be.kdg.swiftby.security;

import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailService implements UserDetailsService {
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
                         Collections.emptyList()
                 ))
                 .orElseThrow(() -> new UsernameNotFoundException(username));
     }
}
