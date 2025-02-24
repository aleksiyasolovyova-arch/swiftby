package be.kdg.swiftby.service.dto.mapper;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.service.dto.FacilityDto;
import org.mapstruct.Mapper;
import org.springframework.boot.SpringApplication;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface FacilityMapper {
    Facility toFacility(FacilityDto facilityDto);
}
