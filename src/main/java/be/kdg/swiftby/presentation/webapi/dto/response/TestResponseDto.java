package be.kdg.swiftby.presentation.webapi.dto.response;

import be.kdg.swiftby.service.TestState;
import be.kdg.swiftby.service.TestType;

import java.time.Instant;
import java.util.UUID;

public record TestResponseDto(
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
