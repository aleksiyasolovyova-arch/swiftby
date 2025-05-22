package be.kdg.swiftby.service.dto;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BikeReportAggregationDto {

    private Long bikeId;
    private LocalDateTime reportTime;
    private Double avgMileage;
    private Double avgAssistanceLevel;
    private Double horizontalInclination;
    private Double verticalInclination;
    private Double batteryCurrent;
    private Double batteryVoltage;
    private Double batteryCapacity;
    private Double batteryTemperature;
    private Double torqueCrank;
    private Double cadence;
    private Double rollerTorque;
    private Double loadCell;
    private Double rol;
    private Double wheelSpeed;
    private Double wheelPower;
    private boolean chargeStatus;
    private boolean statusPlug;

    public BikeReportAggregationDto(Long bikeId,
                                    LocalDateTime reportTime,
                                    Double avgMileage,
                                    Double avgAssistanceLevel,
                                    Double horizontalInclination,
                                    Double verticalInclination,
                                    Double batteryCurrent,
                                    Double batteryVoltage,
                                    Double batteryCapacity,
                                    Double batteryTemperature,
                                    Double torqueCrank,
                                    Double cadence,
                                    Double rollerTorque,
                                    Double loadCell,
                                    Double rol,
                                    Double wheelSpeed,
                                    Double wheelPower,
                                    boolean chargeStatus,
                                    boolean statusPlug) {
        this.bikeId = bikeId;
        this.reportTime = reportTime;
        this.avgMileage = avgMileage;
        this.avgAssistanceLevel = avgAssistanceLevel;
        this.horizontalInclination = horizontalInclination;
        this.verticalInclination = verticalInclination;
        this.batteryCurrent = batteryCurrent;
        this.batteryVoltage = batteryVoltage;
        this.batteryCapacity = batteryCapacity;
        this.batteryTemperature = batteryTemperature;
        this.torqueCrank = torqueCrank;
        this.cadence = cadence;
        this.rollerTorque = rollerTorque;
        this.loadCell = loadCell;
        this.rol = rol;
        this.wheelSpeed = wheelSpeed;
        this.wheelPower = wheelPower;
        this.chargeStatus = chargeStatus;
        this.statusPlug = statusPlug;
    }
    public static BikeReportSummary toSummary(BikeReportAggregationDto aggregation, BikeInstance bikeInstance) {
        BikeReportSummary summary = new BikeReportSummary();
        summary.setBikeInstance(bikeInstance);
        summary.setTorque(bikeInstance.getModel().getMotor().getTorque());
        summary.setMaxPower(bikeInstance.getModel().getMotor().getMaxPower());
        summary.setNominalPower(bikeInstance.getModel().getMotor().getNominalPower());
        summary.setEngineType(bikeInstance.getModel().getMotor().getEngineType());
        summary.setGearType(bikeInstance.getModel().getMotor().getGearType());

        summary.setReportTime(aggregation.getReportTime().toLocalDate());
        summary.setAvgMileage(aggregation.getAvgMileage());
        summary.setAvgAssistanceLevel(aggregation.getAvgAssistanceLevel());
        summary.setHorizontalInclination(aggregation.getHorizontalInclination());
        summary.setVerticalInclination(aggregation.getVerticalInclination());
        summary.setBatteryCurrent(aggregation.getBatteryCurrent());
        summary.setVoltage(aggregation.getBatteryVoltage());
        summary.setCapacity(aggregation.getBatteryCapacity());
        summary.setTemperature(aggregation.getBatteryTemperature());
        summary.setTorqueCrank(aggregation.getTorqueCrank());
        summary.setCadence(aggregation.getCadence());
        summary.setRollerTorque(aggregation.getRollerTorque());
        summary.setLoadCell(aggregation.getLoadCell());
        summary.setRol(aggregation.getRol());
        summary.setSpeed(aggregation.getWheelSpeed());
        summary.setPower(aggregation.getWheelPower());
        summary.setChargeStatus(aggregation.isChargeStatus());
        summary.setStatusPlug(aggregation.isStatusPlug());

        summary.setTechnicianComment("Auto-generated summary based on report aggregation.");

        return summary;
    }

}
