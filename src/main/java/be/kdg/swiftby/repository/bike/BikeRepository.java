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
    @Query("""
    SELECT DISTINCT b FROM Bike b
    LEFT JOIN FETCH b.ownerships bo
    LEFT JOIN FETCH bo.owner
    WHERE b.id = :id
""")
    Optional<Bike> findByIdWithOwnerships(@Param("id") Long id);

}
