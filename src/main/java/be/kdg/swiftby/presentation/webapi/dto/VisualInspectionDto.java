package be.kdg.swiftby.presentation.webapi.dto;

import be.kdg.swiftby.domain.bike.Condition;

public record VisualInspectionDto(
//        Long summaryId,
        Condition tires,
        Condition cranks,
        Condition electricalWiring,
        Condition frameFork,
        Condition grips,
        Condition chainBelt,
        Condition pedals,
        Condition reflectors,
        Condition brakePads,
        Condition brakeLevers,
        Condition brakeCables,
        Condition brakeDiscs,
        Condition gearCables,
        Condition mudguards,
        Condition handlebarStem,
        Condition rearSprocket,
        Condition frontSprocket,
        Condition rimSpokes,
        Condition rearSuspension,
        Condition frontSuspension,
        Condition saddle
) {}
