package be.kdg.swiftby.repository.bike;

import be.kdg.swiftby.domain.bike.Bike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
