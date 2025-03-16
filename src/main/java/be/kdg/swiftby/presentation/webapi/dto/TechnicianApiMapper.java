package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.presentation.webapi.dto.response.TechnicianApiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TechnicianApiMapper {
    /* fixme
    json always gets all of the relations (lazy loading) but idk if it should. i replaced it with only the id for now
    context: technician <--*---1--   facility   --1---*--> administrator
     */
    @Mapping(source = "facility.id", target = "facilityId")
    List<TechnicianApiResponseDto> toTechnicianApiRequestDtoList(List<Technician> technicians);

    @Mapping(source = "facility.id", target = "facilityId")
    TechnicianApiResponseDto toTechnicianApiRequestDto(Technician technician);
}
