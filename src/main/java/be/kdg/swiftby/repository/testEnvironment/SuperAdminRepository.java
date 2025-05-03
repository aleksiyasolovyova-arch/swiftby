package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    boolean existsByEmail(String email);
    Optional<SuperAdmin> findByEmail(String username);
}
