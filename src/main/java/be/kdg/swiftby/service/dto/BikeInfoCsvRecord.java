package be.kdg.swiftby.service.dto;

public record BikeInfoCsvRecord(
        String workshopName,
        String workshopCity,
        String workshopCountry,
        Long testBenchNumber,
        String reviewDate,
        String mechanicFirstName,
        String mechanicLastName,
        String bikeOwnerFirstName,
        String bikeOwnerLastName,
        String brand,
        String type,
        String chassisNumber,
        String productionDate,
        String bikeSize,
        int mileageKm,
        String gearType,
        String engineType,
        String powertrain,
        int accuCapacityWh,
        int maxSupportPercent,
        int enginePowerMaxW,
        int enginePowerNominalW,
        int engineTorqueNm
) {}
