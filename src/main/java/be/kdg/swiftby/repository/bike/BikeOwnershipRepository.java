package be.kdg.swiftby.repository.bike;

import be.kdg.swiftby.domain.bike.BikeOwnership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BikeOwnershipRepository extends JpaRepository<BikeOwnership, Long> {
    List<BikeOwnership> findByOwnerId(Long ownerId);
}
