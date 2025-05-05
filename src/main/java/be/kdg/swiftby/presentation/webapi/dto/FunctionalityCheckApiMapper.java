package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.report.FunctionalityCheck;
import be.kdg.swiftby.service.dto.FunctionalCheckDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FunctionalityCheckApiMapper {
    FunctionalityCheck toEntity(FunctionalCheckDTO dto);
    FunctionalCheckDTO toDto(FunctionalityCheck entity);
}
