package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.service.TestType;
import be.kdg.swiftby.service.dto.api.dto.TestDto;

import java.util.UUID;

public interface TestBenchApiService {
    TestDto startTest(
            TestType testType,
            int batteryCapacity,
            int maxSupport,
            int enginePowerMax,
            int enginePowerNominal,
            int engineTorque,
            Long bikeId
    );

    TestDto getTest(UUID testId);

    void getReport(UUID testId);
}
