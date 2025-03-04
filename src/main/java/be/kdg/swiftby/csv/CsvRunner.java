package be.kdg.swiftby.csv;

import be.kdg.swiftby.service.intf.BikeReportService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CsvRunner implements CommandLineRunner {
    private final CsvService csvService;
    private final BikeReportService bikeReportService;

    public CsvRunner( CsvService csvService, BikeReportService bikeReportService) {
        this.csvService = csvService;
        this.bikeReportService = bikeReportService;
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = "src/main/resources/uploads/Dummy_data_e_bike_testbench_Data.csv";
        List<BikeReportCsvRecord> records = csvService.parseCsv(filePath);
        for (BikeReportCsvRecord record : records) {
            bikeReportService.save(
                    1L,
                    record.reportTime(),
                    record.mileage(),
                    record.assistanceLevel(),
                    record.technicianComment(),
                    record.axialSensorDataDto(),
                    record.batteryDataDto(),
                    record.motorDataDto(),
                    record.pedalDataDto(),
                    record.testBenchDataDto(),
                    record.wheelDataDto()
            );
        }
    }

}
