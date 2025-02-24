package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.BikeOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeOwnerRepository extends JpaRepository<BikeOwner, Long> {
}
