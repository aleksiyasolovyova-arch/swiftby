package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.testEnv.SuperAdmin;
import be.kdg.swiftby.presentation.webapi.dto.response.SuperAdminApiRequestDto;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface SuperAdminApiMapper {
    List<SuperAdminApiRequestDto> toSuperAdminApiRequestDtoList(List<SuperAdmin> superAdmins);

    SuperAdminApiRequestDto toSuperAdminApiRequestDto(SuperAdmin superAdmin);
}
