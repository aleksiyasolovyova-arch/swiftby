package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.report.MotorData;
import be.kdg.swiftby.service.dto.MotorDataDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MotorDataMapper {
    MotorData toMotorData(MotorDataDto motorDataDto);
}
