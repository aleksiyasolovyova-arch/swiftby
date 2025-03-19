package be.kdg.swiftby.csv;

import be.kdg.swiftby.service.dto.*;
import jakarta.annotation.PreDestroy;
import lombok.AllArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
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
            throw new IOException("🚨 Reports directory not found: " + REPORTS_DIR);
        }

        // Get the latest CSV file in the folder
        File[] csvFiles = reportsFolder.listFiles((dir, name) -> name.endsWith(".csv"));
        if (csvFiles == null || csvFiles.length == 0) {
            throw new IOException("🚨 No CSV files found in reports directory.");
        }

        File latestFile = csvFiles[0]; // Since we assume only one file, we pick the first
        System.out.println("📂 Processing CSV File: " + latestFile.getName());

        List<BikeReportCsvRecord> records = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new BOMInputStream(new FileInputStream(latestFile)), StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                     .withFirstRecordAsHeader()
                     .withDelimiter(',')
                     .withIgnoreHeaderCase()
                     .withTrim())) {

            System.out.println("✅ CSV Headers: " + csvParser.getHeaderNames());

            for (CSVRecord record : csvParser) {
                BikeReportCsvRecord csvRecord = parseCsvRecord(record);
                records.add(csvRecord);
            }
        }

        System.out.println("✅ Parsed Records Count: " + records.size());

        // Delete file after successful processing
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
                        1L // Default test bench
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
                        System.out.println("🗑️ Deleted CSV file on shutdown: " + file.getName());
                    } else {
                        System.out.println("⚠️ Failed to delete: " + file.getName());
                    }
                }
            }
        }
    }
}



//    public List<BikeReportCsvRecord> processCsv(InputStream csvStream) throws IOException {
//        List<BikeReportCsvRecord> records = new ArrayList<>();
//
//        // Use BOMInputStream to remove Byte Order Mark (BOM)
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new BOMInputStream(csvStream), StandardCharsets.UTF_8));
//             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
//                     .withFirstRecordAsHeader()
//                     .withDelimiter(',')
//                     .withIgnoreHeaderCase()
//                     .withTrim())) {
//
//            System.out.println("📄 Raw CSV Content (First 5 lines):");
//            reader.mark(10000); // Mark the stream so we can reset after printing
//
//            for (int i = 0; i < 5; i++) {
//                String line = reader.readLine();
//                if (line == null) break;
//                System.out.println(line);
//            }
//
//            reader.reset(); // Reset reader to re-read the file
//
//
//            for (CSVRecord record : csvParser) {
//                System.out.println("🔹 Processing record: " + record.toString());
//
//                // Parse fields
//                AxialSensorDataDto axialSensorDataDto = new AxialSensorDataDto(
//                        Double.parseDouble(record.get("horizontalInclination")),
//                        Double.parseDouble(record.get("verticalInclination"))
//                );
//
//                BatteryDataDto batteryDataDto = new BatteryDataDto(
//                        Boolean.parseBoolean(record.get("chargeStatus")),
//                        Double.parseDouble(record.get("batteryVoltage")),
//                        Double.parseDouble(record.get("batteryCurrent")),
//                        Double.parseDouble(record.get("batteryCapacity")),
//                        Double.parseDouble(record.get("batteryTemperatureCelsius"))
//                );
//
//                MotorDataDto motorDataDto = new MotorDataDto(
//                        Double.parseDouble(record.get("engineRpm")),
//                        Double.parseDouble(record.get("enginePowerWatt"))
//                );
//
//                PedalDataDto pedalDataDto = new PedalDataDto(
//                        Double.parseDouble(record.get("torqueCrankNm")),
//                        Double.parseDouble(record.get("cadanceRpm"))
//                );
//
//                TestBenchDataDto testBenchDataDto = new TestBenchDataDto(
//                        Double.parseDouble(record.get("rollTorque")),
//                        Double.parseDouble(record.get("loadcellN")),
//                        Double.parseDouble(record.get("rolHz")),
//                        Integer.parseInt(record.get("loadPower")),
//                        Boolean.parseBoolean(record.get("statusPlug")),
//                        1L // Default test bench (update later if necessary)
//                );
//
//                WheelDataDto wheelDataDto = new WheelDataDto(
//                        Double.parseDouble(record.get("bikeWheelSpeedKmh")),
//                        Double.parseDouble(record.get("wheelPowerWatt"))
//                );
//
//                BikeReportCsvRecord csvRecord = new BikeReportCsvRecord(
//                        LocalDateTime.parse(record.get("timestamp")),
//                        0,
//                        Integer.parseInt(record.get("assistanceLevel")),
//                        "Generated from CSV",
//                        axialSensorDataDto,
//                        batteryDataDto,
//                        motorDataDto,
//                        pedalDataDto,
//                        testBenchDataDto,
//                        wheelDataDto
//                );
//
//                records.add(csvRecord);
//            }
//        }
//
//        return records;
//    }

//    THIS INFO IS NOT PRESENT IN THE CSV
//    public List<BikeInfoCsvRecord> parseBikeInfoCsv(String filePath) throws IOException {
//        List<BikeInfoCsvRecord> records = new ArrayList<>();
//        try (BOMInputStream bomInputStream = new BOMInputStream(new FileInputStream(filePath));
//             Reader reader = new InputStreamReader(bomInputStream, StandardCharsets.UTF_8);
//             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
//                     .withDelimiter(';')
//                     .withFirstRecordAsHeader()
//                     .withIgnoreHeaderCase()
//                     .withTrim())) {
//
//            Map<String, String> headerMap = new HashMap<>();
//            csvParser.getHeaderMap().forEach((header, index) -> headerMap.put(header.trim(), header));
//
//            for (CSVRecord record : csvParser) {
//                BikeInfoCsvRecord bikeInfo = new BikeInfoCsvRecord(
//                        record.get(headerMap.get("Name Workshop")),
//                        record.get(headerMap.get("Location Workshop City")),
//                        record.get(headerMap.get("Location Workshop Country")),
//                        1L,
//                        record.get(headerMap.get("Review Date")),
//                        record.get(headerMap.get("First Name Mechanic")),
//                        record.get(headerMap.get("Last Name Mechanic")),
//                        record.get(headerMap.get("First Name Bike Owner")),
//                        record.get(headerMap.get("Last Name Bike Owner")),
//                        record.get(headerMap.get("Brand")),
//                        record.get(headerMap.get("Type")),
//                        record.get(headerMap.get("Chassisnumber")),
//                        record.get(headerMap.get("Production Date")),
//                        record.get(headerMap.get("Bike Size")),
//                        Integer.parseInt(record.get(headerMap.get("Mileage (km)"))),
//                        record.get(headerMap.get("Gear Type")),
//                        record.get(headerMap.get("Engine Type")),
//                        record.get(headerMap.get("Powertrain")),
//                        Integer.parseInt(record.get(headerMap.get("Accu Capacity (Wh)"))),
//                        Integer.parseInt(record.get(headerMap.get("Max Support (%)"))),
//                        Integer.parseInt(record.get(headerMap.get("Engine power - max (W)"))),
//                        Integer.parseInt(record.get(headerMap.get("Engine power - nominal (W)"))),
//                        Integer.parseInt(record.get(headerMap.get("Engine torque (Nm)")))
//                );
//
//                records.add(bikeInfo);
//            }
//        }
//        return records;
//    }}
