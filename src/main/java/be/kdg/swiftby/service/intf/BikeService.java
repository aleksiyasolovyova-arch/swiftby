package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.service.dto.BikeDto;

import java.util.List;

public interface BikeService {
    List<Bike> getAll();

    Bike getById(Long id);

    Bike getByIdWithOwner(Long id);

    List<Bike> getByBikeOwnerId(Long id);

    Bike save(BikeDto bikeDto);

    List<Bike> findByMotorEngineType(String engineType);

    void remove(Long id);

    List<Bike> getAllByFacilityId(Long facilityId);

}
