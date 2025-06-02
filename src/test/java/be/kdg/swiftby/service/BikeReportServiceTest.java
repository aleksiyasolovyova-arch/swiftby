package be.kdg.swiftby.service;

import be.kdg.swiftby.TestUtils;
import be.kdg.swiftby.config.DotenvInitializer;
import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.domain.bike.BikeOwnership;
import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.service.intf.BikeReportService;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = DotenvInitializer.class)
public class BikeReportServiceTest {
    @Autowired
    private BikeReportSummaryService sut;
    @Autowired
    private TestUtils testUtils;

    @Test
    void shouldReturnAllBikeReports() {
        //Arrange
        BikeModel bikeModel = testUtils.createBikeModel();
        BikeInstance bikeInstance = testUtils.createBikeInstance("ABC-48652", bikeModel.getId());
        BikeInstance bikeInstance2 = testUtils.createBikeInstance("DEF-4952", bikeModel.getId());
        BikeInstance bikeInstance3 = testUtils.createBikeInstance("GRR-4684", bikeModel.getId());
        BikeOwner bikeOwner = testUtils.createBikeOwner("Bike", "Owner", bikeInstance.getId());
        testUtils.addBikeOwnershipLink(bikeInstance2, bikeOwner);


        BikeReportSummary bikeReportSummary = testUtils.createBikeReportSummary(bikeInstance.getId());
        BikeReportSummary bikeReportSummary2 = testUtils.createBikeReportSummary(bikeInstance.getId());
        BikeReportSummary otherBikeReportSummary = testUtils.createBikeReportSummary(bikeInstance2.getId());

        BikeReportSummary otherBikeOwnerReportSummary = testUtils.createBikeReportSummary(bikeInstance3.getId());



        //Act
        List<BikeReportSummary> foundBikeReportSummaries = sut.getAllSummariesByBikeOwnerId(bikeOwner.getId());

        //Assert
        assertEquals(3, foundBikeReportSummaries.size());
        assertThat(List.of(bikeReportSummary2, bikeReportSummary, otherBikeReportSummary))
                .usingRecursiveComparison()
                .ignoringFields("bikeInstance")
                .ignoringFields("reports")
                .ignoringFields("functionalityCheck")
                .isEqualTo(foundBikeReportSummaries);
    }

    @BeforeEach
    void cleanUp() {
        testUtils.cleanUp();
    }

}
