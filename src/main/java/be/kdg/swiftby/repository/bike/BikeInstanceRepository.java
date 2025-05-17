package be.kdg.swiftby.repository.bike;

import be.kdg.swiftby.domain.bike.BikeInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BikeInstanceRepository extends JpaRepository<BikeInstance, Long> {
    Optional<BikeInstance> findByChassisNumber(String chassisNumber);

    @Query("""
        SELECT DISTINCT b FROM BikeInstance b
        LEFT JOIN FETCH b.ownerships bo
        LEFT JOIN FETCH bo.owner
        WHERE b.id = :id
    """)
    Optional<BikeInstance> findByIdWithOwnerships(@Param("id") Long id);

    @Query("""
        SELECT b FROM BikeInstance b
        JOIN b.ownerships bo
        JOIN bo.owner ow
        WHERE ow.facility.id = :facilityId
    """)
    List<BikeInstance> findAllByFacilityId(@Param("facilityId") Long facilityId);
    @Query("""
    SELECT b FROM BikeInstance b
    JOIN FETCH b.model m
    JOIN FETCH m.motor
    WHERE b.id = :id
""")
    Optional<BikeInstance> findByIdWithModelAndMotor(@Param("id") Long id);

}
