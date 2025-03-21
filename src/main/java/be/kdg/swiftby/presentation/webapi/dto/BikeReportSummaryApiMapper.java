package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportSummaryDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface BikeReportSummaryApiMapper {
    BikeReportSummaryDto toBikeReportSummaryDto(BikeReportSummary bikeReportSummary);
}
