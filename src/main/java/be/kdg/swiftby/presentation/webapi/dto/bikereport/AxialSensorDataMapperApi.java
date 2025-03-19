package be.kdg.swiftby.presentation.webapi.dto.bikereport;

import be.kdg.swiftby.domain.report.AxialSensorData;
import be.kdg.swiftby.presentation.webapi.dto.response.AxialSensorDataApiResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AxialSensorDataMapperApi {
    AxialSensorDataApiResponseDto toAxialSensorDataDto(AxialSensorData axialSensorData);
}
