package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.presentation.webapi.dto.response.AdministratorApiResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AdministratorApiMapper {
    @Mapping(source = "facility.id", target = "facilityId")
    AdministratorApiResponseDto toAdminDto(Administrator administrator);

    @Mapping(source = "facility.id", target = "facilityId")
    List<AdministratorApiResponseDto> toAdminDtoList(List<Administrator> administrators);


}
