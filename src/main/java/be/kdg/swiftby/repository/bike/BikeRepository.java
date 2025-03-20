package be.kdg.swiftby.repository.bike;

import be.kdg.swiftby.domain.bike.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
    List<Bike> findByMotorEngineType(String engineType);
    Optional<Bike> findBikeByChassisNumber(String chassisNumber);
    List<Bike> findByBikeOwner_Id(Long ownerId);
    @Query("SELECT b FROM Bike b JOIN FETCH b.bikeOwner WHERE b.id = :id")
    Optional<Bike> findByIdWithOwner(@Param("id") Long id);

}
