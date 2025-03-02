package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.*;
import be.kdg.swiftby.repository.report.*;
import be.kdg.swiftby.service.dto.*;
import be.kdg.swiftby.service.intf.BikeReportService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class BikeReportServiceImpl implements BikeReportService {

    private final BikeReportRepository bikeReportRepository;
    private final AxialSensorDataRepository axialSensorDataRepository;
    private final BatteryDataRepository batteryDataRepository;
    private final MotorDataRepository motorDataRepository;
    private final PedalDataRepository pedalDataRepository;
    private final TestBenchDataRepository testBenchDataRepository;

    public BikeReportServiceImpl(BikeReportRepository bikeReportRepository, AxialSensorDataRepository axialSensorDataRepository, BatteryDataRepository batteryDataRepository, MotorDataRepository motorDataRepository, PedalDataRepository pedalDataRepository, TestBenchDataRepository testBenchDataRepository) {
        this.bikeReportRepository = bikeReportRepository;
        this.axialSensorDataRepository = axialSensorDataRepository;
        this.batteryDataRepository = batteryDataRepository;
        this.motorDataRepository = motorDataRepository;
        this.pedalDataRepository = pedalDataRepository;
        this.testBenchDataRepository = testBenchDataRepository;
    }


    @Override
    public List<BikeReport> getAll() {
        return bikeReportRepository.findAll();
    }

    @Override
    public BikeReport getById(Long id) {
        return bikeReportRepository.findById(id).orElseThrow(() -> new RuntimeException("Bike report not found"));
    }

    @Override
    public BikeReport save(LocalDate reportTime, int mileage, int assistanceLevel,
                           String technicianComment, AxialSensorDataDto axialSensorDataDto,
                           BatteryDataDto batteryDataDto, MotorDataDto motorDataDto,
                           PedalDataDto pedalDataDto, TestBenchDataDto testBenchDataDto,
                           WheelDataDto wheelDataDto) {

        AxialSensorData axialSensorData = new AxialSensorData(axialSensorDataDto);
        BatteryData batteryData = new BatteryData(batteryDataDto);
        MotorData motorData = new MotorData(motorDataDto);
        PedalData pedalData = new PedalData(pedalDataDto);
        TestBenchData testBenchData = new TestBenchData(testBenchDataDto);
        WheelData wheelData = new WheelData(wheelDataDto);

        BikeReport bikeReport = new BikeReport(
                null, 0, 0,
                technicianComment, axialSensorData, batteryData,
                motorData, pedalData, testBenchData, wheelData
        );
        return bikeReportRepository.save(bikeReport);
    }

    @Override
    public void remove(Long id) {
        bikeReportRepository.removeBikeReportById(id);
    }
}
