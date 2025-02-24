package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.repository.testEnvironment.TestBenchRepository;
import be.kdg.swiftby.service.intf.TestBenchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestBenchServiceImpl implements TestBenchService {
    TestBenchRepository testBenchRepository;

    public TestBenchServiceImpl(TestBenchRepository testBenchRepository) {
        this.testBenchRepository = testBenchRepository;
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

    }
}
