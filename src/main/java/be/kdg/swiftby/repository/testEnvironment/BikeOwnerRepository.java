package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.BikeOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BikeOwnerRepository extends JpaRepository<BikeOwner, Long> {
    boolean existsByEmail(String email);

    Optional<BikeOwner> findByEmail(String email);

}
