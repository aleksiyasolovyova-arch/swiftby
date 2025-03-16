package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.AxialSensorData;
import be.kdg.swiftby.service.dto.AxialSensorDataDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AxialSensorDataMapper {
    AxialSensorData toAxialSensorData(AxialSensorDataDto axialSensorDataDto);
}
