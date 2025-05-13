package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    boolean existsByEmail(String email);

    Optional<SuperAdmin> findByEmail(String username);
}
