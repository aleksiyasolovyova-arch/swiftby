package be.kdg.swiftby.repository.testEnvironment;

import be.kdg.swiftby.domain.testEnv.BikeOwner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeOwnerRepository extends JpaRepository<BikeOwner, Long> {
    boolean existsByEmail(String email);

    Optional<BikeOwner> findByEmail(String email);
    List<BikeOwner> findByEmailContainingIgnoreCase(String email);
    List<BikeOwner> findAllByFacilityId(Long facilityId);
    Optional<BikeOwner> findByFacilityIdAndId(Long facilityId, Long id);

}
