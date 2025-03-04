package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.presentation.webapi.dto.request.FacilityApiResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FacilityMapper {
    Facility toFacility(FacilityApiResponseDto facilityApiResponseDto);
}
