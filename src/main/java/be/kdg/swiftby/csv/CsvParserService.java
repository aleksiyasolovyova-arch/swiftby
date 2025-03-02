package be.kdg.swiftby.csv;


import be.kdg.swiftby.service.dto.*;
import be.kdg.swiftby.service.intf.BikeReportService;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Service
public class CsvParserService {
    private final BikeReportService bikeReportService;

    public CsvParserService(BikeReportService bikeReportService) {
        this.bikeReportService = bikeReportService;
    }

    public void parseAndPrintCsv(String filePath) throws FileNotFoundException {
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

            List<String> headers = csvParser.getHeaderNames();

            for (CSVRecord record : csvParser) {
                System.out.println("---- New Record ----");

                LocalDate reportTime = LocalDate.now();
                int mileage = 0;
                int assistanceLevel = 0;
                String technicianComment = "yappa yappa technician comment";

                AxialSensorDataDto axialSensorDataDto = new AxialSensorDataDto(
                        Double.parseDouble(record.get("Hozizontal inclination sensor")),
                        Double.parseDouble(record.get("Vertical inclination sensor"))
                );

                BatteryDataDto batteryDataDto = new BatteryDataDto(
                        Integer.parseInt(record.get("Charge Status")) == 1,
                        Double.parseDouble(record.get("Battery Voltage (V)")),
                        Double.parseDouble(record.get("Battery Current (A)")),
                        Double.parseDouble(record.get("Battery Capacity (%)")),
                        Double.parseDouble(record.get("Battery Temperature (°C)"))
                );

                MotorDataDto motorDataDto = new MotorDataDto(
                        Double.parseDouble(record.get("Engine (rpm)")),
                        Double.parseDouble(record.get("Engine Power (W)"))
                );

                PedalDataDto pedalDataDto = new PedalDataDto(
                        Double.parseDouble(record.get("Torque Crank (Nm)")),
                        Double.parseDouble(record.get("Cadance (rpm)"))
                );

                TestBenchDataDto testBenchDataDto = new TestBenchDataDto(
                        Double.parseDouble(record.get("Rol Troque (Nm)")),
                        Double.parseDouble(record.get("Loadcell (N)")),
                        Double.parseDouble(record.get("Rol (Hz)")),
                        Integer.parseInt(record.get("Load Power (%)")),
                        Integer.parseInt(record.get("Charge Status")) == 1
                );

                WheelDataDto wheelDataDto = new WheelDataDto(
                        Double.parseDouble(record.get("Bike wheel speed (km/h)")),
                        Double.parseDouble(record.get("Wheel Power (W)"))
                );

                bikeReportService.save(
                        reportTime, mileage, assistanceLevel, technicianComment,
                        axialSensorDataDto, batteryDataDto, motorDataDto,
                        pedalDataDto, testBenchDataDto, wheelDataDto
                );

                for (String header : headers) {
                    System.out.println(header + ": " + record.get(header));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
