package be.kdg.swiftby.csv;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CsvRunner implements CommandLineRunner {

    private final CsvParserService csvParserService;

    public CsvRunner(CsvParserService csvParserService) {
        this.csvParserService = csvParserService;
    }

    @Override
    public void run(String... args) throws Exception {
        String filePath = "src/main/resources/uploads/Dummy_data_e_bike_testbench_Data.csv";
        csvParserService.parseAndPrintCsv(filePath);
    }

}
