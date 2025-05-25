package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.report.*;
import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.repository.bike.BikeInstanceRepository;
import be.kdg.swiftby.repository.bike.BikeModelRepository;
import be.kdg.swiftby.repository.report.*;
import be.kdg.swiftby.repository.testEnvironment.TestBenchRepository;
import be.kdg.swiftby.service.dto.*;
import be.kdg.swiftby.service.dto.mapper.*;
import be.kdg.swiftby.service.intf.BikeReportService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class BikeReportServiceImpl implements BikeReportService {

    private final BikeReportRepository bikeReportRepository;
    private final BikeReportSummaryRepository bikeReportSummaryRepository;
    private final BikeInstanceRepository bikeInstanceRepository;
    private final TestBenchRepository testBenchRepository;
    private final AxialSensorDataRepository axialSensorDataRepository;
    private final BatteryDataRepository batteryDataRepository;
    private final MotorDataRepository motorDataRepository;
    private final PedalDataRepository pedalDataRepository;
    private final TestBenchDataRepository testBenchDataRepository;
    private final WheelDataRepository wheelDataRepository;
    private final AxialSensorDataMapper axialSensorDataMapper;
    private final BatteryDataMapper batteryDataMapper;
    private final MotorDataMapper motorDataMapper;
    private final PedalDataMapper pedalDataMapper;
    private final TestBenchDataMapper testBenchDataMapper;
    private final WheelDataMapper wheelDataMapper;
    private final BikeModelRepository bikeModelRepository;
    FunctionalityCheckRepository functionalityCheckRepository;

    @Override
    public List<BikeReport> getAll() {
        return bikeReportRepository.findAll();
    }

    @Override
    public List<BikeReport> getAllWithBikes() {
        return bikeReportRepository.getAllWithBikes();
    }

    @Override
    public BikeReport getById(Long id) {
        return bikeReportRepository.findById(id)
                .orElseThrow(() -> NotFoundException.forBikeReport(id));
    }

    @Override
    public BikeReport save(
            Long bikeInstanceId,
            LocalDateTime reportTime,
            int mileage,
            int assistanceLevel,
            String technicianComment,
            AxialSensorDataDto axialSensorDataDto,
            BatteryDataDto batteryDataDto,
            MotorDataDto motorDataDto,
            PedalDataDto pedalDataDto,
            TestBenchDataDto testBenchDataDto,
            WheelDataDto wheelDataDto
    ) {
        BikeInstance bikeInstance = bikeInstanceRepository.findById(bikeInstanceId)
                .orElseThrow(() -> NotFoundException.forBike(bikeInstanceId));
        TestBench testBench = testBenchRepository.findById(testBenchDataDto.testBenchDataId())
                .orElseThrow(() -> NotFoundException.forTestBench(testBenchDataDto.testBenchDataId()));

        TestBenchData testBenchData = testBenchDataMapper.toTestBench(testBenchDataDto);
        testBenchData.setTestBench(testBench);
        testBenchData = testBenchDataRepository.save(testBenchData);

        AxialSensorData axialSensorData = axialSensorDataRepository.save(axialSensorDataMapper.toAxialSensorData(axialSensorDataDto));
        BatteryData batteryData = batteryDataRepository.save(batteryDataMapper.toBatteryData(batteryDataDto));
        MotorData motorData = motorDataRepository.save(motorDataMapper.toMotorData(motorDataDto));
        PedalData pedalData = pedalDataRepository.save(pedalDataMapper.toPedalData(pedalDataDto));
        WheelData wheelData = wheelDataRepository.save(wheelDataMapper.toWheelData(wheelDataDto));

        BikeReport bikeReport = new BikeReport();
        bikeReport.setReportTime(reportTime);
        bikeReport.setMileage(mileage);
        bikeReport.setAssistanceLevel(assistanceLevel);
        bikeReport.setTechnicianComment(technicianComment);
        bikeReport.setAxialSensorData(axialSensorData);
        bikeReport.setBatteryData(batteryData);
        bikeReport.setMotorData(motorData);
        bikeReport.setPedalData(pedalData);
        bikeReport.setTestBenchData(testBenchData);
        bikeReport.setWheelData(wheelData);
        bikeReport.setBike(bikeInstance);

        return bikeReportRepository.save(bikeReport);
    }


    // TODO: USE THIS INSTEAD OF AGGREGATING IN JAVA
//    @Override
//    public BikeReportSummary saveReportSummary(Long bikeId, LocalDate reportDate) {
//        BikeReportSummary summary = bikeReportSummaryRepository.getBikeReportSummary(bikeId, reportDate);
//        return bikeReportSummaryRepository.save(summary);
//    }

    @Override
    public void remove(Long id) {
        if (!bikeReportRepository.existsById(id)) {
            throw NotFoundException.forBikeReport(id);
        }
        bikeReportRepository.deleteById(id);
    }

    @Override
    public List<BikeReport> getReportsBySummaryId(Long summaryId) {
        return bikeReportRepository.findBySummaryId(summaryId);
    }

    @Override
    public BikeReport aggregatedReport(Long reportId) {
        return null;
    }

    @Transactional
    public void attachFunctionalityCheck(Long summaryId, Long checkId) {
        BikeReportSummary summary = bikeReportSummaryRepository.findById(summaryId).get();

        FunctionalityCheck check = functionalityCheckRepository.findById(checkId).get();

        summary.setFunctionalityCheck(check);
        bikeReportSummaryRepository.save(summary);
    }

    @Override
    public BikeReportSummary saveReportSummaryFromSavedReports(List<Long> savedReportIds) {
        BikeReportAggregationDto aggregation = bikeReportRepository.aggregateReports(savedReportIds);

        BikeInstance bikeInstance = bikeInstanceRepository.findById(aggregation.getBikeId())
                .orElseThrow(() -> NotFoundException.forBike(aggregation.getBikeId()));

        BikeReportSummary summary = BikeReportAggregationDto.toSummary(aggregation, bikeInstance);

        BikeReportSummary savedSummary = bikeReportSummaryRepository.save(summary);

        List<BikeReport> reports = bikeReportRepository.findAllById(savedReportIds);
        reports.forEach(report -> report.setSummary(savedSummary));
        bikeReportRepository.saveAll(reports);

        return savedSummary;
    }

    @Override
    public List<BikeReport> findAllByBikeOwnerId(Long bikeOwnerId) {
        return bikeReportRepository.findAllByBikeOwnerId(bikeOwnerId);
    }


}
