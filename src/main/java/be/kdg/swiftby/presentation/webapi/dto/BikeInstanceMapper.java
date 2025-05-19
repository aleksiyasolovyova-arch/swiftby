package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeInstanceRequestDto;
import org.springframework.stereotype.Component;

@Component
public class BikeInstanceMapper {

    public BikeInstanceDto toDto(BikeInstance bike) {
        BikeModel model = bike.getModel();
        return new BikeInstanceDto(
                bike.getId(),
                bike.getChassisNumber(),
                model.getId(),
                model.getBrand(),
                model.getType(),
                model.getBatteryCapacity(),
                model.getBikeSize()
        );
    }

    public BikeInstance fromRequestDto(BikeInstanceRequestDto dto, BikeModel model) {
        BikeInstance instance = new BikeInstance();
        instance.setChassisNumber(dto.chassisNumber());
        instance.setModel(model);
        return instance;
    }
}
