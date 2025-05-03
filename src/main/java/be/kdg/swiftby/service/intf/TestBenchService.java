package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.TestBenchData;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.TestBench;

import java.util.List;
import java.util.UUID;

public interface TestBenchService {
    List<TestBench> getAll();
    TestBench getById(Long id);
    TestBench save(Boolean isActive, Facility facility);

    void remove(Long id);

    List<TestBench> getAllByFacilityId(Long id);

    TestBench getByFacilityIdAndTestBenchId(Long facilityId, Long testBenchId);

    void removeAllByFacilityId(Long id);

}
