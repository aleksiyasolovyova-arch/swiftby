package be.kdg.swiftby.service.dto;

import java.util.List;

public record ReportChartSeriesDto(
        String label,
        List<Double> values,
        List<String> timeLabels )
 {}
