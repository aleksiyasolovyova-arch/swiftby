package be.kdg.swiftby.presentation.webapi.dto.request;

import be.kdg.swiftby.service.TestType;

public record TestRequestDto(
        TestType type,
        int batteryCapacity,
        int maxSupport,
        int enginePowerMax,
        int enginePowerNominal,
        int engineTorque
) {
}
