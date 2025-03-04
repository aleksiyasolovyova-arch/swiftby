package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.report.*;
import be.kdg.swiftby.repository.bike.BikeRepository;
import be.kdg.swiftby.repository.report.*;
import be.kdg.swiftby.service.dto.*;
import be.kdg.swiftby.service.dto.mapper.*;
import be.kdg.swiftby.service.intf.BikeReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class BikeReportServiceImpl implements BikeReportService {

    BikeReportRepository bikeReportRepository;
    BikeRepository bikeRepository;

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
            LocalDate reportTime,
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
        AxialSensorData axialSensorData = axialSensorDataRepository.save(axialSensorDataMapper.toAxialSensorData(axialSensorDataDto));
        BatteryData batteryData = batteryDataRepository.save(batteryDataMapper.toBatteryData(batteryDataDto));
        MotorData motorData = motorDataRepository.save(motorDataMapper.toMotorData(motorDataDto));
        PedalData pedalData = pedalDataRepository.save(pedalDataMapper.toPedalData(pedalDataDto));
        TestBenchData testBenchData = testBenchDataRepository.save(testBenchDataMapper.toTestBench(testBenchDataDto));
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

    @Override
    public void remove(Long id) {
        if (!bikeReportRepository.existsById(id)) {
            throw NotFoundException.forBikeReport(id);
        }
        bikeReportRepository.deleteById(id);
    }


}
