package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.presentation.webapi.dto.response.FacilityApiResponseDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FacilityApiMapper {
    FacilityApiResponseDto toFacilityApiRequestDto(Facility facility);
    List<FacilityApiResponseDto> toFacilityApiRequestDtoList(List<Facility> facilities);
}
