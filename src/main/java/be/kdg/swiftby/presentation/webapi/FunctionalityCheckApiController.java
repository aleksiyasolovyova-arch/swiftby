package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.service.dto.FunctionalCheckDTO;
import be.kdg.swiftby.service.intf.FunctionalityCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/functional-checks")
@RequiredArgsConstructor
public class FunctionalityCheckApiController {

    private final FunctionalityCheckService service;

    @PostMapping("/{testId}")
    public ResponseEntity<FunctionalCheckDTO> saveFunctionalCheck(
            @PathVariable UUID testId,
            @RequestBody FunctionalCheckDTO dto) {
        dto.setTestId(testId); // Ensure the testId is set in the DTO
        FunctionalCheckDTO saved = service.save(testId, dto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{testId}")
    public ResponseEntity<FunctionalCheckDTO> getFunctionalCheckByTestId(@PathVariable UUID testId) {
        FunctionalCheckDTO found = service.getByTestId(testId);
        return ResponseEntity.ok(found);
    }



}
