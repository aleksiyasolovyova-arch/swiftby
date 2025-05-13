package be.kdg.swiftby.presentation.webapi.dto.bikereport;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportApiResponseDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

// uses tells mapstruct what mappers it should use
// (we need it since we have nested dto classes)
@Mapper(componentModel = SPRING,
        uses = {
                MotorDataMapperApi.class,
                WheelDataMapperApi.class,
                BatteryDataMapperApi.class,
                PedalDataMapperApi.class,
                AxialSensorDataMapperApi.class,
                TestBenchDataMapperApi.class,
                BikeMapperApi.class
        })
public interface BikeReportMapperApi {
    BikeReportApiResponseDto toBikeReportDto(BikeReport bikeReport);
}
