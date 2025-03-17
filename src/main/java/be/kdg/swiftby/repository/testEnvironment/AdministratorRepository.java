package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    Optional<Administrator> findAdministratorByEmail(String email);
    boolean existsByEmail(String email);

    List<Administrator> findAllByFacilityId(Long facilityId);

    void deleteAllByFacilityId(Long id);

    Optional<Administrator> findByFacilityIdAndId(Long facilityId, Long id);
}
