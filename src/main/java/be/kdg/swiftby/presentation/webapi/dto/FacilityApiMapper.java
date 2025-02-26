package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.presentation.webapi.dto.request.FacilityApiRequestDto;
import be.kdg.swiftby.service.dto.FacilityDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FacilityApiMapper {
    FacilityApiRequestDto toFacilityApiRequestDto(Facility facility);
    List<FacilityApiRequestDto> toFacilityApiRequestDtoList(List<Facility> facilities);
}
