package be.kdg.swiftby.domain.report;

import be.kdg.swiftby.domain.bike.Condition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class VisualInspection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Condition tires;

    @Enumerated(EnumType.STRING)
    private Condition cranks;

    @Enumerated(EnumType.STRING)
    private Condition electricalWiring;

    @Enumerated(EnumType.STRING)
    private Condition frameFork;

    @Enumerated(EnumType.STRING)
    private Condition grips;

    @Enumerated(EnumType.STRING)
    private Condition chainBelt;

    @Enumerated(EnumType.STRING)
    private Condition pedals;

    @Enumerated(EnumType.STRING)
    private Condition reflectors;

    @Enumerated(EnumType.STRING)
    private Condition brakePads;

    @Enumerated(EnumType.STRING)
    private Condition brakeLevers;

    @Enumerated(EnumType.STRING)
    private Condition brakeCables;

    @Enumerated(EnumType.STRING)
    private Condition brakeDiscs;

    @Enumerated(EnumType.STRING)
    private Condition gearCables;

    @Enumerated(EnumType.STRING)
    private Condition mudguards;

    @Enumerated(EnumType.STRING)
    private Condition handlebarStem;

    @Enumerated(EnumType.STRING)
    private Condition rearSprocket;
    @Enumerated(EnumType.STRING)

    private Condition frontSprocket;

    @Enumerated(EnumType.STRING)
    private Condition rimSpokes;

    @Enumerated(EnumType.STRING)
    private Condition rearSuspension;

    @Enumerated(EnumType.STRING)
    private Condition frontSuspension;

    @Enumerated(EnumType.STRING)
    private Condition saddle;

}
