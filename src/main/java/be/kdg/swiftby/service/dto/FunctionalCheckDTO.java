package be.kdg.swiftby.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionalCheckDTO {
    private UUID testId;
    private String lightsStatus;
    private String brakesStatus;
    private String displayStatus;
    private String hornStatus;
    private String motorStatus;
    private String batteryStatus;
}
