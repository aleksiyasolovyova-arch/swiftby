package be.kdg.swiftby.csv;

import be.kdg.swiftby.service.dto.*;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
                        Integer.parseInt(record.get("Charge Status")) == 1
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
}
