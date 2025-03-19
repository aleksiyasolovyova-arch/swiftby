package be.kdg.swiftby.repository.bike;

import be.kdg.swiftby.domain.bike.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Long> {
    List<Bike> findByMotorEngineType(String engineType);
}
