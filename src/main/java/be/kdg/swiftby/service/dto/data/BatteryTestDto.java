package be.kdg.swiftby.service.dto.data;

public record BatteryTestDto(
        double availableCapacityWh,
        int promisedCapacityWh,
        double batteryHealthPercent,
        double score
) {}

