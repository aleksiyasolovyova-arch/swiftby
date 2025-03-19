package be.kdg.swiftby.presentation.webapi.dto.bikereport;

import be.kdg.swiftby.domain.report.BatteryData;
import be.kdg.swiftby.presentation.webapi.dto.response.BatteryDataApiResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BatteryDataMapperApi {
    BatteryDataApiResponseDto toBatteryDataDto(BatteryData batteryData);
}
