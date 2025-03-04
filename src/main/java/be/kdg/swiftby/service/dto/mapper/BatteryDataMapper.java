package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.BatteryData;
import be.kdg.swiftby.service.dto.BatteryDataDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BatteryDataMapper {
    BatteryData toBatteryData(BatteryDataDto batteryDataDto);
}
