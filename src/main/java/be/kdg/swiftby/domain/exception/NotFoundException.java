package be.kdg.swiftby.domain.exception;

import org.aspectj.weaver.ast.Not;

public class NotFoundException extends RuntimeException {
    private NotFoundException(String message) {
        super(message);
    }

    public static NotFoundException forBike(Long id) {
        return new NotFoundException(String.format("Bike not found for id %s", id));
    }

    public static NotFoundException forMotor(Long id) {
        return new NotFoundException(String.format("Motor not found for id %s", id));
    }

    public static NotFoundException forAxialSensorData(Long id) {
        return new NotFoundException(String.format("AxialSensorData not found for id %s", id));
    }

    public static NotFoundException forBatteryData(Long id) {
        return new NotFoundException(String.format("BatteryData not found for id %s", id));
    }

    public static NotFoundException forBikeReport(Long id) {
        return new NotFoundException(String.format("BikeReport not found for id %s", id));
    }

    public static NotFoundException forMotorData(Long id) {
        return new NotFoundException(String.format("MotorData not found for id %s", id));
    }

    public static NotFoundException forPedalData(Long id) {
        return new NotFoundException(String.format("PedalData not found for id %s", id));
    }

    public static NotFoundException forTestBenchData(Long id) {
        return new NotFoundException(String.format("TestBenchData not found for id %s", id));
    }

    public static NotFoundException forWheelData(Long id) {
        return new NotFoundException(String.format("WheelData not found for id %s", id));
    }

    public static NotFoundException forEmployee(Long id) {
        return new NotFoundException(String.format("Employee not found for id %s", id));
    }

    public static NotFoundException forBikeOwner(Long id) {
        return new NotFoundException(String.format("BikeOwner not found for id %s", id));
    }

    public static NotFoundException forFacility(Long id) {
        return new NotFoundException(String.format("Facility not found for id %s", id));
    }

    public static NotFoundException forSuperAdmin(Long id) {
        return new NotFoundException(String.format("SuperAdministrator not found for id %s", id));
    }

    public static NotFoundException forTechnician(Long id) {
        return new NotFoundException(String.format("Technician not found for id %s", id));
    }

    public static NotFoundException forTestBench(Long id) {
        return new NotFoundException(String.format("TestBench not found for id %s", id));
    }

    public static NotFoundException forAdmin(Long id) {
        return new NotFoundException(String.format("Admin not found for id %s", id));
    }

    public static NotFoundException forAdminEmail(String email) {
        return new NotFoundException(String.format("Admin not found with email %s", email));
    }
    public static NotFoundException forUserWithEmail(String email) {
        return new NotFoundException(String.format("User not found with email %s", email));
    }

}
