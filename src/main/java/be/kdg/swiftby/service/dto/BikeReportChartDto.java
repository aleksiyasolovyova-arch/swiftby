package be.kdg.swiftby.service.dto;

import java.time.LocalDateTime;

public record BikeReportChartDto(
        LocalDateTime reportTime,
        Double voltage,
        Double current,
        Double enginePower,
        Double temperature
) {}
