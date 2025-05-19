package be.kdg.swiftby.service;

import be.kdg.swiftby.TestUtils;
import be.kdg.swiftby.config.DotenvInitializer;
import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.domain.bike.BikeOwnership;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.repository.bike.BikeInstanceRepository;
import be.kdg.swiftby.repository.bike.BikeOwnershipRepository;
import be.kdg.swiftby.service.intf.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@ContextConfiguration(initializers = DotenvInitializer.class)
public class UserServiceTest {
    @Autowired
    private UserService sut;
    @Autowired
    private TestUtils testUtils;
    @Autowired
    private BikeOwnershipRepository bikeOwnershipRepository;
    @Autowired
    private BikeInstanceRepository bikeInstanceRepository;

    @Test
    void shouldReturnTechnician() {
        //Arrange
        Facility facility = testUtils.createFacility("facility");
        Technician technician = testUtils.createTechnician(facility.getId(), "Tech", "Nician");

        //Act
        Technician user = (Technician) sut.getUserByEmail(technician.getEmail());

        //Assert
        assertThat(user)
                .usingRecursiveComparison()
                .ignoringFields("facility")
                .isEqualTo(technician)
        ;
        assertEquals(technician.getFacility().getId(), user.getFacility().getId());
        assertEquals(Technician.class, technician.getClass());
    }

    @Test
    void shouldReturnAdministrator() {
        //Arrange
        Facility facility = testUtils.createFacility("facility");
        Administrator admin = testUtils.createAdministrator(facility.getId(), "Ad", "Min");

        //Act
        Administrator user = (Administrator) sut.getUserByEmail(admin.getEmail());

        //Assert
        assertThat(user)
                .usingRecursiveComparison()
                .ignoringFields("facility")
                .isEqualTo(admin)
        ;
        assertEquals(admin.getFacility().getId(), user.getFacility().getId());
        assertEquals(Administrator.class, admin.getClass());
    }

    @Test
    void shouldReturnBikeOwnerWithAssociatedBike() {
        //Arrange
        BikeModel bikeModel = testUtils.createBikeModel();
        BikeInstance bikeInstance = testUtils.createBikeInstance("ABC-458", bikeModel.getId());
        BikeOwner bikeOwner = testUtils.createBikeOwner("Bike", "Owner", bikeInstance.getId());

        //Act
        BikeOwner user = (BikeOwner) sut.getUserByEmail(bikeOwner.getEmail()); // get the user
        BikeInstance foundBike = bikeInstanceRepository.findByBikeOwnerId(user.getId()).orElseThrow(); // get the bike the user should have

        //Assert
        assertThat(bikeModel)
                .usingRecursiveComparison()
                .ignoringFields("motor")
                .isEqualTo(foundBike.getModel());
        assertThat(bikeInstance)
                .usingRecursiveComparison()
                .ignoringFields("model")
                .ignoringFields("reports")
                .ignoringFields("ownerships")
                .ignoringFields("summaries")
                .isEqualTo(foundBike);
        assertThat(user)
                .usingRecursiveComparison()
                .ignoringFields("ownerships")
                .isEqualTo(bikeOwner)
        ;
        assertEquals(BikeOwner.class, bikeOwner.getClass());
    }

    @BeforeEach
    void cleanUp() {
        testUtils.cleanUp();
    }
}
