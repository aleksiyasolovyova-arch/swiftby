package be.kdg.swiftby.presentation.webapi.dto.response;

import be.kdg.swiftby.service.dto.FunctionalCheckDTO;

import java.time.LocalDate;
import java.util.List;

public record BikeReportSummaryDto(
        Long id,
        Long bikeInstanceId,
        LocalDate reportTime,
        double avgMileage,
        double avgAssistanceLevel,
        double horizontalInclination,
        double verticalInclination,
        boolean chargeStatus,
        double batteryCurrent,
        double voltage,
        double capacity,
        double temperature,
        String engineType,
        String gearType,
        Integer maxPower,
        Integer nominalPower,
        Integer torque,
        double torqueCrank,
        double cadence,
        double rollerTorque,
        double loadCell,
        double rol,
        int loadPower,
        boolean statusPlug,
        double speed,
        double power,
        String technicianComment,
        List<Long> reportIds,
        Long functionalityCheckId,
        String bearingHealth,
        Long visualInspectionId


) {}
