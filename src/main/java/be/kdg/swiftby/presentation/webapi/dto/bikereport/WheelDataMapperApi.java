package be.kdg.swiftby.presentation.webapi.dto.bikereport;

import be.kdg.swiftby.domain.report.WheelData;
import be.kdg.swiftby.service.dto.WheelDataDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface WheelDataMapperApi {
    WheelDataDto toWheelDataDto(WheelData wheelData);
}
