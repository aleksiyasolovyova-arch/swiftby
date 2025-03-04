package be.kdg.swiftby.repository.bike;

import be.kdg.swiftby.domain.bike.Motor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MotorRepository extends JpaRepository<Motor, Long> {
}
