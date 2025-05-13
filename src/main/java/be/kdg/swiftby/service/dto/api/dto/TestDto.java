package be.kdg.swiftby.service.dto.api.dto;

import be.kdg.swiftby.service.TestState;
import be.kdg.swiftby.service.TestType;

import java.time.Instant;
import java.util.UUID;

public record TestDto(
        UUID id,
        Instant expiryDate,
        TestState state,
        TestType type,
        int batteryCapacity,
        int maxSupport,
        int enginePowerMax,
        int enginePowerNominal,
        int engineTorque
) {
}
