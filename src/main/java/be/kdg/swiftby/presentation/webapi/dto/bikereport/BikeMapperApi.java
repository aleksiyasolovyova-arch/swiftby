package be.kdg.swiftby.presentation.webapi.dto.bikereport;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.presentation.webapi.dto.request.BikeRequestDto;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeApiResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BikeMapperApi {
    BikeApiResponseDto toBikeDto(BikeModel bikeModel);
    BikeModel toBike(BikeRequestDto bikeRequestDto);
    List<BikeApiResponseDto> toBikeDtoList(List<BikeModel> bikeModels);
    List<BikeApiResponseDto> toBikeInstanceDtoList(List<BikeInstance> bikeInstances);
    BikeApiResponseDto toBikeDto(BikeInstance instance);

}
