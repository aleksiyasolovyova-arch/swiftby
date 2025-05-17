package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.service.dto.BikeModelDto;

import java.util.List;

public interface BikeModelService {
    List<BikeModel> getAll();

    BikeModel save(BikeModelDto bikeModelDto);

    List<BikeModel> findByMotorEngineType(String engineType);

    void remove(Long id);
    BikeModel getById(Long id);
}
