package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.repository.testEnvironment.FacilityRepository;
import be.kdg.swiftby.repository.testEnvironment.TestBenchRepository;
import be.kdg.swiftby.service.intf.TestBenchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestBenchServiceImpl implements TestBenchService {
    TestBenchRepository testBenchRepository;

    FacilityRepository facilityRepository;

    Logger log = LoggerFactory.getLogger(TestBenchServiceImpl.class);

    public TestBenchServiceImpl(TestBenchRepository testBenchRepository, FacilityRepository facilityRepository) {
        this.testBenchRepository = testBenchRepository;
        this.facilityRepository = facilityRepository;
    }

    @Override
    public List<TestBench> getAll() {
        return testBenchRepository.findAll();
    }

    @Override
    public TestBench getById(Long id) {
        return testBenchRepository.findById(id).orElseThrow(() -> NotFoundException.forTestBench(id));
    }

    @Override
    public TestBench save(Boolean isActive, Facility facility) {
        return testBenchRepository.save(new TestBench(isActive, facility));
    }

    @Override
    public void remove(Long id) {
        testBenchRepository.deleteById(id);
    }

    @Override
    public List<TestBench> getAllByFacilityId(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forFacility(id));
        List<TestBench> testBenches = testBenchRepository.findAllByFacility(facility);
        log.debug(String.format("Got all testbenches for facility with id: %s - %s", id, testBenches));
        return testBenches;
    }

    @Override
    public TestBench getByFacilityIdAndTestBenchId(Long facilityId, Long testBenchId) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> NotFoundException.forFacility(facilityId));
        TestBench testBench = testBenchRepository.findByFacilityAndId(facility, testBenchId)
                .orElseThrow(() -> NotFoundException.forTestBench(testBenchId));
        log.debug(String.format("Got testbench for facility with id: %s -  %s", testBenchId, testBench));
        return testBench;
    }

    @Override
    public void removeAllByFacilityId(Long id) {
        Facility facility = facilityRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forFacility(id));
        testBenchRepository.removeAllByFacility(facility);
        log.debug(String.format("Removed all testbenches for facility with id: %s", id));
    }



}
