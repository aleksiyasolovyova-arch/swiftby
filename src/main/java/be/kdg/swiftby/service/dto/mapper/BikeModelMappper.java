package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.service.dto.BikeModelDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = MotorMapper.class)
public interface BikeModelMappper {
    BikeModel toBikeModel(BikeModelDto bikeModelDto);
    BikeModelDto toBikeModelDto(BikeModel bikeModel);
}