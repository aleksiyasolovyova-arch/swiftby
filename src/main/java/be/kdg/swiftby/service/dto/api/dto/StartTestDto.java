package be.kdg.swiftby.service.dto.api.dto;

import be.kdg.swiftby.service.TestType;

public record StartTestDto(
        TestType type,
        int batteryCapacity,
        int maxSupport,
        int enginePowerMax,
        int enginePowerNominal,
        int engineTorque
) {
}
