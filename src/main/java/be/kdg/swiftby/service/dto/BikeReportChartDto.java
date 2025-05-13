package be.kdg.swiftby.service.dto;

public record BikeReportChartDto(
        double batteryVoltage,
        double batteryCurrent,
        double batteryTemperature,
        double enginePower,
        double wheelPower,
        double torqueCrank,
        double rollerTorque,
        double cadence,
        double speed,
        double horizontalInclination,
        double verticalInclination,
        long time

) {
}
