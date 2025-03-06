package be.kdg.swiftby.csv;

import be.kdg.swiftby.service.dto.*;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class CsvService {

    public List<BikeReportCsvRecord> parseCsv(String filePath) throws IOException {
        List<BikeReportCsvRecord> records = new ArrayList<>();

        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord record : csvParser) {
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
                        Integer.parseInt(record.get("Charge Status")) == 1,
                        // now the default test bench is 1
                        // TODO: Change later when we can determine which test report corresponds to which test bench
                        1L
                );

                WheelDataDto wheelDataDto = new WheelDataDto(
                        Double.parseDouble(record.get("Bike wheel speed (km/h)")),
                        Double.parseDouble(record.get("Wheel Power (W)"))
                );
                BikeReportCsvRecord csvRecord = new BikeReportCsvRecord(
                        LocalDate.now(),
                        0,
                        0,
                        "yappa yappa comment",
                        axialSensorDataDto,
                        batteryDataDto,
                        motorDataDto,
                        pedalDataDto,
                        testBenchDataDto,
                        wheelDataDto
                );
                records.add(csvRecord);
            }
        }
        return records;
    }

    public List<BikeInfoCsvRecord> parseBikeInfoCsv(String filePath) throws IOException {
        List<BikeInfoCsvRecord> records = new ArrayList<>();
        try (BOMInputStream bomInputStream = new BOMInputStream(new FileInputStream(filePath));
             Reader reader = new InputStreamReader(bomInputStream, StandardCharsets.UTF_8);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withDelimiter(';')
                     .withFirstRecordAsHeader()
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            Map<String, String> headerMap = new HashMap<>();
            csvParser.getHeaderMap().forEach((header, index) -> headerMap.put(header.trim(), header));

            for (CSVRecord record : csvParser) {
                BikeInfoCsvRecord bikeInfo = new BikeInfoCsvRecord(
                        record.get(headerMap.get("Name Workshop")),
                        record.get(headerMap.get("Location Workshop City")),
                        record.get(headerMap.get("Location Workshop Country")),
                        1L,
                        record.get(headerMap.get("Review Date")),
                        record.get(headerMap.get("First Name Mechanic")),
                        record.get(headerMap.get("Last Name Mechanic")),
                        record.get(headerMap.get("First Name Bike Owner")),
                        record.get(headerMap.get("Last Name Bike Owner")),
                        record.get(headerMap.get("Brand")),
                        record.get(headerMap.get("Type")),
                        record.get(headerMap.get("Chassisnumber")),
                        record.get(headerMap.get("Production Date")),
                        record.get(headerMap.get("Bike Size")),
                        Integer.parseInt(record.get(headerMap.get("Mileage (km)"))),
                        record.get(headerMap.get("Gear Type")),
                        record.get(headerMap.get("Engine Type")),
                        record.get(headerMap.get("Powertrain")),
                        Integer.parseInt(record.get(headerMap.get("Accu Capacity (Wh)"))),
                        Integer.parseInt(record.get(headerMap.get("Max Support (%)"))),
                        Integer.parseInt(record.get(headerMap.get("Engine power - max (W)"))),
                        Integer.parseInt(record.get(headerMap.get("Engine power - nominal (W)"))),
                        Integer.parseInt(record.get(headerMap.get("Engine torque (Nm)")))
                );

                records.add(bikeInfo);
            }
        }
        return records;
    }

}
