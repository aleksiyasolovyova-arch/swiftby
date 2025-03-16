package be.kdg.swiftby.repository.bike;

import be.kdg.swiftby.domain.bike.Motor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotorRepository extends JpaRepository<Motor, Long> {
    Optional<Motor> findByEngineType(String engineType);
}
