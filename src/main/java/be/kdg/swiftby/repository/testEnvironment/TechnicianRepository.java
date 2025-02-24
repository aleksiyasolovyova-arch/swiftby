package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.Technician;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnicianRepository extends JpaRepository<Technician, Long> {
}
