package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.report.VisualInspection;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VisualInspectionMapper {
    VisualInspection toEntity(VisualInspectionDto dto);
    VisualInspectionDto toDto(VisualInspection inspection);
}
