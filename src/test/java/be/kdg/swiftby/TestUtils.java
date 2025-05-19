package be.kdg.swiftby;

import be.kdg.swiftby.domain.bike.*;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.domain.testEnv.BikeOwner;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.domain.testEnv.Technician;
import be.kdg.swiftby.repository.bike.BikeInstanceRepository;
import be.kdg.swiftby.repository.bike.BikeModelRepository;
import be.kdg.swiftby.repository.bike.BikeOwnershipRepository;
import be.kdg.swiftby.repository.bike.MotorRepository;
import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
import be.kdg.swiftby.repository.testEnvironment.BikeOwnerRepository;
import be.kdg.swiftby.repository.testEnvironment.FacilityRepository;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Random;
import java.util.UUID;

@Component
public class TestUtils {
    @Autowired
    private FacilityRepository facilityRepository;
    @Autowired
    private TechnicianRepository technicianRepository;
    @Autowired
    private AdministratorRepository administratorRepository;
    @Autowired
    private BikeOwnerRepository bikeOwnerRepository;
    @Autowired
    private BikeModelRepository bikeModelRepository;
    @Autowired
    private MotorRepository motorRepository;
    @Autowired
    private BikeOwnershipRepository bikeOwnershipRepository;
    @Autowired
    private BikeInstanceRepository bikeInstanceRepository;


    public void cleanUp() {
        bikeOwnershipRepository.deleteAll();
        bikeInstanceRepository.deleteAll();
        technicianRepository.deleteAll();
        administratorRepository.deleteAll();
        bikeOwnerRepository.deleteAll();
        facilityRepository.deleteAll();
        bikeModelRepository.deleteAll();
    }

    public Facility createDummyFacility() {
        Facility facility = new Facility("facility-" + UUID.randomUUID(), "email@email.com", "Belgium",
                "Antwerp", "2000", "Street-" + UUID.randomUUID(), "4", "bus 4");
        return facilityRepository.save(facility);
    }

    public Facility createFacility(String facilityName) {
        Facility facility = new Facility(facilityName, "email@email.com", "Belgium",
                "Antwerp", "2000", "Street-" + UUID.randomUUID(), "4", "bus 4");
        return facilityRepository.save(facility);
    }

    public Technician createTechnician(Long facilityId, String firstName, String lastName) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow();
        Technician technician = new Technician(facility, String.format("%s.%s@email.com", firstName, lastName),
                "password", firstName, lastName, "+32014000000");
        return technicianRepository.save(technician);
    }

    public Technician createDummyTechnician(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow();
        Technician technician = new Technician(facility, String.format("%s@email.com", "name-" + UUID.randomUUID()), "password",
                "name-" + UUID.randomUUID(), "lastName-" + UUID.randomUUID(),
                "+32014000000");
        return technicianRepository.save(technician);
    }

    public Administrator createAdministrator(Long facilityId, String firstName, String lastName) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow();
        Administrator admin = new Administrator(facility, String.format("%s.%s@email.com", firstName, lastName),
                "password", firstName, lastName, "+32014000000");
        return administratorRepository.save(admin);
    }

    public Administrator createDummyAdministrator(Long facilityId) {
        Facility facility = facilityRepository.findById(facilityId).orElseThrow();
        Administrator admin = new Administrator(facility, String.format("%s@email.com", "name-" + UUID.randomUUID()), "password",
                "name-" + UUID.randomUUID(), "lastName-" + UUID.randomUUID(),
                "+32014000000");
        return administratorRepository.save(admin);
    }

    public BikeOwner createBikeOwner(String firstName, String lastName) {
        BikeOwner bikeOwner = new BikeOwner(String.format("%s.%s@email.com", firstName, lastName), "password",
                "name-" + UUID.randomUUID(), "lastName-" + UUID.randomUUID(),
                "+32014000000");
        return bikeOwnerRepository.save(bikeOwner);
    }

    @Transactional
    public BikeOwner createBikeOwner(String firstName, String lastName, Long bikeInstanceId) {
        BikeInstance bikeInstance = bikeInstanceRepository.findById(bikeInstanceId).orElseThrow();

        BikeOwner bikeOwner = addBikeOwner();
        bikeOwner.setFirstName(firstName);
        bikeOwner.setLastName(lastName);

        bikeOwner = bikeOwnerRepository.save(bikeOwner);

        addBikeOwnershipLink(bikeInstance, bikeOwner);
        return bikeOwnerRepository.findByEmail(bikeOwner.getEmail()).orElseThrow();
    }

    public BikeOwner createDummyBikeOwner() {
        BikeOwner bikeOwner = addBikeOwner();
        return bikeOwnerRepository.save(bikeOwner);
    }

    public BikeModel createBikeModel() {
        Random random = new Random();
        Motor motor = createMotor();

        BikeModel bikeModel = new BikeModel("Brand-" + UUID.randomUUID(),
                "Electric Mountain Bike",
                POWERTRAIN.BELT,
                BIKE_SIZE.L,
                random.nextInt(5, 20),
                random.nextInt(150, 700),
                motor
        );

        return bikeModelRepository.save(bikeModel);
    }

    public BikeInstance createBikeInstance(String chassisNumber, Long bikeModelId) {
        BikeModel bikeModel = bikeModelRepository.findById(bikeModelId).orElseThrow();
        BikeInstance bikeInstance = new BikeInstance(chassisNumber, bikeModel);

        return bikeInstanceRepository.save(bikeInstance);
    }

    public Motor createMotor() {
        Random random = new Random();
        Motor motor = new Motor("Brushless DC",
                "Automatic", random.nextInt(300, 750),
                random.nextInt(150, 500),
                random.nextInt(15, 85));

        return motorRepository.save(motor);
    }

    public BikeOwnership addBikeOwnershipLink(BikeInstance bikeInstance, BikeOwner bikeOwner) {
        BikeOwnership bikeOwnership = bikeOwnershipRepository.save(new BikeOwnership(bikeInstance, bikeOwner));
        bikeInstance.getOwnerships().add(bikeOwnership);
        bikeOwner.getOwnerships().add(bikeOwnership);
        bikeInstanceRepository.save(bikeInstance);
        bikeOwnerRepository.save(bikeOwner);
        return bikeOwnership;
    }

    private BikeOwner addBikeOwner() {
        BikeOwner bikeowner = new BikeOwner(String.format("%s@email.com", "name-" + UUID.randomUUID()), "password",
                "name-" + UUID.randomUUID(), "lastName-" + UUID.randomUUID(),
                "+32014000000");
        bikeowner.setOwnerships(new HashSet<>());
        return bikeowner;
    }


}
