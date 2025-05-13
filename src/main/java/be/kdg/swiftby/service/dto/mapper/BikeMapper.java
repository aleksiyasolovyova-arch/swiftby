package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.service.dto.BikeDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = MotorMapper.class)
public interface BikeMapper {
    Bike toBike(BikeDto bikeDto);

    BikeDto toBikeDto(Bike bike);
}