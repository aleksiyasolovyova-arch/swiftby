package be.kdg.swiftby.presentation.webapi.dto.bikereport;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeApiResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BikeMapperApi {
    BikeApiResponseDto toBikeDto(Bike bike);
    Bike toBike(BikeRequestDto bikeRequestDto);
}
