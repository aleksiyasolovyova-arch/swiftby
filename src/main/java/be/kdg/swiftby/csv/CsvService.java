package be.kdg.swiftby.csv;

import be.kdg.swiftby.service.dto.*;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.input.BOMInputStream;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CsvService {

    //    timestamp,batteryVoltage,batteryCurrent,batteryCapacity,batteryTemperatureCelcius,
//    chargeStatus,assistanceLevel,torqueCrankNm,bikeWheelSpeedKmh,cadanceRpm,engineRpm,
//    enginePowerWatt,wheelPowerWatt,rollTorque,loadcellN,rolHz,horizontalInclination,
//    verticalInclination,loadPower,statusPlug
    // Add this import
    private static final String REPORTS_DIR = "reports/";

    public List<BikeReportCsvRecord> processLatestCsvFile() throws IOException {
        File reportsFolder = new File(REPORTS_DIR);
        if (!reportsFolder.exists() || !reportsFolder.isDirectory()) {
            throw new IOException("Reports directory not found: " + REPORTS_DIR);
        }
        File[] csvFiles = reportsFolder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            throw new IOException("No CSV files found in reports directory.");
        }
        File latestFile = csvFiles[0];
        System.out.println("Processing CSV File: " + latestFile.getName());

        List<BikeReportCsvRecord> records = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new BOMInputStream(new FileInputStream(latestFile)), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withDelimiter(',')
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            System.out.println("CSV Headers: " + csvParser.getHeaderNames());

            for (CSVRecord record : csvParser) {
                BikeReportCsvRecord csvRecord = parseCsvRecord(record);
                records.add(csvRecord);
            }
        }

        System.out.println("Parsed Records Count: " + records.size());

        if (!records.isEmpty()) {
            Files.delete(latestFile.toPath());
            System.out.println(" CSV file deleted: " + latestFile.getName());
        } else {
            System.out.println(" No valid records found. CSV file retained.");
        }

        return records;
    }

    private BikeReportCsvRecord parseCsvRecord(CSVRecord record) {
        return new BikeReportCsvRecord(
                LocalDateTime.parse(record.get("timestamp")),
                0,
                Integer.parseInt(record.get("assistanceLevel")),
                "Generated from CSV",
                new AxialSensorDataDto(
                        Double.parseDouble(record.get("horizontalInclination")),
                        Double.parseDouble(record.get("verticalInclination"))
                ),
                new BatteryDataDto(
                        Boolean.parseBoolean(record.get("chargeStatus")),
                        Double.parseDouble(record.get("batteryVoltage")),
                        Double.parseDouble(record.get("batteryCurrent")),
                        Double.parseDouble(record.get("batteryCapacity")),
                        Double.parseDouble(record.get("batteryTemperatureCelsius"))
                ),
                new MotorDataDto(
                        Double.parseDouble(record.get("engineRpm")),
                        Double.parseDouble(record.get("enginePowerWatt"))
                ),
                new PedalDataDto(
                        Double.parseDouble(record.get("torqueCrankNm")),
                        Double.parseDouble(record.get("cadanceRpm"))
                ),
                new TestBenchDataDto(
                        Double.parseDouble(record.get("rollTorque")),
                        Double.parseDouble(record.get("loadcellN")),
                        Double.parseDouble(record.get("rolHz")),
                        Integer.parseInt(record.get("loadPower")),
                        Boolean.parseBoolean(record.get("statusPlug")),
                        1L
                ),
                new WheelDataDto(
                        Double.parseDouble(record.get("bikeWheelSpeedKmh")),
                        Double.parseDouble(record.get("wheelPowerWatt"))
                )
        );
    }

    @PreDestroy
    public void cleanReportsDirectoryOnShutdown() {
        File reportsFolder = new File(REPORTS_DIR);

        if (reportsFolder.exists() && reportsFolder.isDirectory()) {
            File[] csvFiles = reportsFolder.listFiles((dir, name) -> name.endsWith(".csv"));

            if (csvFiles != null) {
                for (File file : csvFiles) {
                    boolean deleted = file.delete();
                    if (deleted) {
                        System.out.println("Deleted CSV file on shutdown: " + file.getName());
                    } else {
                        System.out.println(" Failed to delete: " + file.getName());
                    }
                }
            }
        }
    }
}