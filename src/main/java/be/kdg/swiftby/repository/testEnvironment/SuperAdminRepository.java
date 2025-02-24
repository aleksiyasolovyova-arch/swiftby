package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
}
