package be.kdg.swiftby.csv;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.service.dto.BikeInfoCsvRecord;
import be.kdg.swiftby.service.dto.FacilityDto;
import be.kdg.swiftby.service.intf.BikeReportService;
import be.kdg.swiftby.service.intf.FacilityService;
import be.kdg.swiftby.service.intf.TechnicianService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class CsvRunner implements CommandLineRunner {
    private final CsvService csvService;
    private final BikeReportService bikeReportService;
    private final FacilityService facilityService;
    private final TechnicianService technicianService;

    public CsvRunner(CsvService csvService, BikeReportService bikeReportService, FacilityService facilityService, TechnicianService technicianService) {
        this.csvService = csvService;
        this.bikeReportService = bikeReportService;
        this.facilityService = facilityService;
        this.technicianService = technicianService;
    }
    @Override
    public void run(String... args) throws Exception {
        processBikeReportCsv();
        processBikeInfoCsv();
    }

    private void processBikeReportCsv() throws Exception {
        String bikeReportFilePath = "src/main/resources/uploads/Dummy_data_e_bike_testbench_Data.csv";
        List<BikeReportCsvRecord> bikeReportRecords = csvService.parseCsv(bikeReportFilePath);

        for (BikeReportCsvRecord record : bikeReportRecords) {
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

    private void processBikeInfoCsv() throws Exception {
        String bikeInfoFilePath = "src/main/resources/uploads/General_Info.csv";
        // TODO: change to one single record, since that's the result we should get from the csv
        List<BikeInfoCsvRecord> bikeInfoRecords = csvService.parseBikeInfoCsv(bikeInfoFilePath);

        System.out.println("DUCK");
        for (BikeInfoCsvRecord infoRecord : bikeInfoRecords) {
            Facility savedFacility = facilityService.save(new FacilityDto(
                    infoRecord.workshopName(),
                    // there's no email in the facility
                    // provided so for now some dummy email
                    "dumdum@email.com",
                    infoRecord.workshopCountry(),
                    infoRecord.workshopCity(),
                    // no zipcode either
                    "2610",
                    // no street either
                    "slayful street",
                    // no street number either
                    "streetie",
                    // no address extra
                    "super extra"
            ));
            Technician savedTechnician = technicianService.saveTechnician(
                    savedFacility,
                    // TODO: there's no email in the report
                    // but when the report would be upploaded
                    // we could extract it from the active user
                    // or it might be provided with an api
                    "technician@email.com",
                    "technician",
                    infoRecord.mechanicFirstName(),
                    infoRecord.mechanicLastName(),
                    // no phone number either, hence the dummy number
                    "+3800000000"
            );
            // TODO: save the bike



            System.out.println("Workshop: " + infoRecord.workshopName());
            System.out.println("Mechanic: " + infoRecord.mechanicFirstName() + " " + infoRecord.mechanicLastName());
            System.out.println("Bike Brand: " + infoRecord.brand());
            System.out.println("Mileage: " + infoRecord.mileageKm());
        }
    }
}
