package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.report.FunctionalityCheck;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportSummaryDto;
import be.kdg.swiftby.service.dto.FunctionalCheckDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BikeReportSummaryApiMapper {
    @Mapping(source = "functionalityCheck.id", target = "functionalityCheckId")
    BikeReportSummaryDto toBikeReportSummaryDto(BikeReportSummary bikeReportSummary);

}
