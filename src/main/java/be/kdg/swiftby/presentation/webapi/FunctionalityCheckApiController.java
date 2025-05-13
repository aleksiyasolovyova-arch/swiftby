package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.report.FunctionalityCheck;
import be.kdg.swiftby.service.dto.FunctionalCheckDTO;
import be.kdg.swiftby.service.intf.FunctionalityCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/functional-checks")
@RequiredArgsConstructor
public class FunctionalityCheckApiController {

    private final FunctionalityCheckService service;

    @PostMapping
    public ResponseEntity<Long> saveFunctionalCheck(@RequestBody FunctionalCheckDTO dto) {
        FunctionalityCheck check = new FunctionalityCheck();
        check.setLightsStatus(dto.getLightsStatus());
        check.setBrakesStatus(dto.getBrakesStatus());
        check.setDisplayStatus(dto.getDisplayStatus());
        check.setHornStatus(dto.getHornStatus());
        check.setMotorStatus(dto.getMotorStatus());
        check.setBatteryStatus(dto.getBatteryStatus());

        FunctionalityCheck saved = service.save(check);
        return ResponseEntity.ok(saved.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FunctionalCheckDTO> getFunctionalCheck(@PathVariable Long id) {
        FunctionalityCheck check = service.findById(id);

        FunctionalCheckDTO dto = new FunctionalCheckDTO(
                check.getLightsStatus(),
                check.getBrakesStatus(),
                check.getDisplayStatus(),
                check.getHornStatus(),
                check.getMotorStatus(),
                check.getBatteryStatus()
        );

        return ResponseEntity.ok(dto);
    }
}
