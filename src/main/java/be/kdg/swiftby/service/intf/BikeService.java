package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.service.dto.BikeModelDto;

import java.util.List;

public interface BikeService {
    List<BikeModel> getAll();
    BikeModel getById(Long id);
    BikeModel getByIdWithOwner(Long id);
    List<BikeModel> getByBikeOwnerId(Long id);
    BikeModel save(BikeModelDto bikeModelDto);
    List<BikeModel> findByMotorEngineType(String engineType);
    void remove(Long id);
    List<BikeModel> getAllByFacilityId(Long facilityId);

}
