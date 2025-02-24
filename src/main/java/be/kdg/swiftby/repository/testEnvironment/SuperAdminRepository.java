package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
}
