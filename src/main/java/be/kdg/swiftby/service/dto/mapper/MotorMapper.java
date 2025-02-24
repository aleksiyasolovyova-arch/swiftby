package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.bike.Motor;
import be.kdg.swiftby.service.dto.MotorDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface MotorMapper {
    Motor toMotor(MotorDto motorDto);
}
