package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.report.*;
import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.repository.bike.BikeRepository;
import be.kdg.swiftby.repository.report.*;
import be.kdg.swiftby.repository.testEnvironment.TestBenchRepository;
import be.kdg.swiftby.service.dto.*;
import be.kdg.swiftby.service.dto.mapper.*;
import be.kdg.swiftby.service.intf.BikeReportService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BikeReportServiceImpl implements BikeReportService {

    BikeReportRepository bikeReportRepository;
    BikeReportSummaryRepository bikeReportSummaryRepository;
    BikeRepository bikeRepository;
    TestBenchRepository testBenchRepository;

    AxialSensorDataRepository axialSensorDataRepository;
    BatteryDataRepository batteryDataRepository;
    MotorDataRepository motorDataRepository;
    PedalDataRepository pedalDataRepository;
    TestBenchDataRepository testBenchDataRepository;
    WheelDataRepository wheelDataRepository;


    AxialSensorDataMapper axialSensorDataMapper;
    BatteryDataMapper batteryDataMapper;
    MotorDataMapper motorDataMapper;
    PedalDataMapper pedalDataMapper;
    TestBenchDataMapper testBenchDataMapper;
    WheelDataMapper wheelDataMapper;

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
                .orElseThrow(()->NotFoundException.forBikeReport(id));
    }

    @Override
    public BikeReport save(
            Long bikeId,
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
        Bike bike = bikeRepository.findById(bikeId)
                .orElseThrow(()->NotFoundException.forBike(bikeId));
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
        bikeReport.setBike(bike);

        return bikeReportRepository.save(bikeReport);
    }
    // TODO: USE THIS INSTEAD OF AGGREGATING IN JAVA
    @Override
    public BikeReportSummary saveReportSummary(Long bikeId, LocalDate reportDate) {
        BikeReportSummary summary = bikeReportSummaryRepository.getBikeReportSummary(bikeId, reportDate);
        return bikeReportSummaryRepository.save(summary);
    }

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
    @Override
    public BikeReportSummary saveReportSummaryFromSavedReports(List<Long> savedReportIds) {
        if (savedReportIds.isEmpty()) {
            throw new IllegalArgumentException(" No reports were saved from the latest CSV file.");
        }

        List<BikeReport> reports = bikeReportRepository.findAllById(savedReportIds);
        // extract bikeId and date from the first report
        Long bikeId = reports.get(0).getBike().getId();
        LocalDate reportDate = reports.get(0).getReportTime().toLocalDate();

        BikeReportSummary summary = new BikeReportSummary();
        summary.setBike(reports.get(0).getBike());
        summary.setReportTime(reportDate);
        summary.setAvgMileage(reports.stream().mapToDouble(BikeReport::getMileage).average().orElse(0));
        summary.setAvgAssistanceLevel(reports.stream().mapToDouble(BikeReport::getAssistanceLevel).average().orElse(0));
        summary.setHorizontalInclination(reports.stream().mapToDouble(r -> r.getAxialSensorData().getHorizontalInclination()).average().orElse(0));
        summary.setVerticalInclination(reports.stream().mapToDouble(r -> r.getAxialSensorData().getVerticalInclination()).average().orElse(0));
        summary.setCurrent(reports.stream().mapToDouble(r -> r.getBatteryData().getCurrent()).average().orElse(0));
        summary.setVoltage(reports.stream().mapToDouble(r -> r.getBatteryData().getVoltage()).average().orElse(0));
        summary.setCapacity(reports.stream().mapToDouble(r -> r.getBatteryData().getCapacity()).average().orElse(0));
        summary.setTemperature(reports.stream().mapToDouble(r -> r.getBatteryData().getTemperature()).average().orElse(0));
        summary.setTorqueCrank(reports.stream().mapToDouble(r -> r.getPedalData().getTorqueCrank()).average().orElse(0));
        summary.setCadence(reports.stream().mapToDouble(r -> r.getPedalData().getCadence()).average().orElse(0));
        summary.setRollerTorque(reports.stream().mapToDouble(r -> r.getTestBenchData().getRollerTorque()).average().orElse(0));
        summary.setLoadCell(reports.stream().mapToDouble(r -> r.getTestBenchData().getLoadCell()).average().orElse(0));
        summary.setRol(reports.stream().mapToDouble(r -> r.getTestBenchData().getRol()).average().orElse(0));
        summary.setSpeed(reports.stream().mapToDouble(r -> r.getWheelData().getSpeed()).average().orElse(0));
        summary.setPower(reports.stream().mapToDouble(r -> r.getWheelData().getPower()).average().orElse(0));

        boolean chargeStatus = reports.stream().anyMatch(r -> r.getBatteryData().isChargeStatus());
        boolean statusPlug = reports.stream().anyMatch(r -> r.getTestBenchData().isStatusPlug());

        summary.setChargeStatus(chargeStatus);
        summary.setStatusPlug(statusPlug);

//        String combinedTechnicianComments = reports.stream()
//                .map(BikeReport::getTechnicianComment)
//                .reduce((a, b) -> a + "; " + b)
//                .orElse("No comments");

        summary.setTechnicianComment("meow summary");

        BikeReportSummary savedSummary = bikeReportSummaryRepository.save(summary);

        reports.forEach(report -> report.setSummary(savedSummary));
        bikeReportRepository.saveAll(reports);

        return summary;
    }


}
