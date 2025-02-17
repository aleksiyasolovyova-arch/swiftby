package be.kdg.swiftby.domain.report;

import lombok.Data;

import java.time.LocalDate;
@Data
public class BikeReport {
    private Long id;
    private LocalDate dateTime;
    private int mileage;
    private int assistanceLevel;
    private String technicianComment;
}
