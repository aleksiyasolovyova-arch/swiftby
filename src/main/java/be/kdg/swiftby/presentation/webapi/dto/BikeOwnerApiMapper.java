package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeOwnerRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeOwnerApiResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)

public interface BikeOwnerApiMapper {
    BikeOwnerApiResponseDto toBikeOwnerDto(BikeOwner bikeOwner);

    List<BikeOwnerApiResponseDto> toBikeOwnerDtoList(List<BikeOwner> bikeOwners);

    Bike toBike(BikeOwnerRequestDto bikeOwnerRequestDto);
}
