package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.report.FunctionalityCheck;
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
    public ResponseEntity<Long> saveFunctionalCheck(
            @RequestBody FunctionalCheckDTO dto
    ) {
        FunctionalityCheck saved = service.save(dto);
        return ResponseEntity.ok(saved.getId());
    }
}
