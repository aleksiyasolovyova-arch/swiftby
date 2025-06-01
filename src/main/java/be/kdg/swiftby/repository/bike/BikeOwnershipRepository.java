package be.kdg.swiftby.repository.bike;

import be.kdg.swiftby.domain.bike.BikeOwnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeOwnershipRepository extends JpaRepository<BikeOwnership, Long> {
    List<BikeOwnership> findByOwnerId(Long ownerId);
    List<BikeOwnership> findByBikeId(Long bikeId);

    boolean existsByOwnerIdAndBikeId(Long ownerId, Long bikeId);
    void deleteByOwnerId(Long ownerId);
}
