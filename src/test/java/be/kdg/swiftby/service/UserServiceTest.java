package be.kdg.swiftby.service;

import be.kdg.swiftby.TestUtils;
import be.kdg.swiftby.config.DotenvInitializer;
import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.bike.BikeOwnership;
import be.kdg.swiftby.domain.testEnv.*;
import be.kdg.swiftby.repository.bike.BikeOwnershipRepository;
import be.kdg.swiftby.repository.bike.BikeRepository;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.service.impl.UserServiceImpl;
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
    private BikeRepository bikeRepository;

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
        Bike bike = testUtils.createBike();
        BikeOwner bikeOwner = testUtils.createBikeOwner("Bike", "Owner", bike.getId());

        //Act
        BikeOwner user = (BikeOwner) sut.getUserByEmail(bikeOwner.getEmail()); // get the user
        BikeOwnership bikeOwnership = bikeOwnershipRepository.findByOwnerId(user.getId()).getFirst(); // get the bike ownership
        System.out.println("bikeownership id:" + bikeOwnership.getId());
        Bike foundBike = bikeRepository.findByBikeOwnershipId(bikeOwnership.getId()).orElseThrow(); // get the bike the user should have

        //Assert
        assertThat(user)
                .usingRecursiveComparison()
                .ignoringFields("ownerships")
                .isEqualTo(bikeOwner)
        ;
        assertThat(bike)
                .usingRecursiveComparison()
                .ignoringFields("motor")
                .ignoringFields("ownerships")
                .ignoringFields("reports")
                .ignoringFields("summaries")
                .isEqualTo(foundBike);
        assertEquals(BikeOwner.class, bikeOwner.getClass());
    }

    @BeforeEach
    void cleanUp() {
        testUtils.cleanUp();
    }
}
