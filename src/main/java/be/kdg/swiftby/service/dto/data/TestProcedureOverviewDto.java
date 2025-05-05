package be.kdg.swiftby.service.dto.data;

public record TestProcedureOverviewDto(
        double maxEnginePowerMeasured,
        double maxEnginePowerPromised,
        double enginePowerDeviation,

        double maxRollerTorqueMeasured,
        double promisedTorque,
        double rollerTorqueDeviation,

        double maxWheelPowerMeasured,
        double promisedWheelPower,
        double wheelPowerDeviation,

        double maxSupport,
        double maxSupportDeviation,

        double overallScore
) {}
