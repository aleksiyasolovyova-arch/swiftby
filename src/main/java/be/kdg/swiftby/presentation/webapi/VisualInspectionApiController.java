package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.report.VisualInspection;
import be.kdg.swiftby.service.impl.VisualInspectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visual-inspections")
public class VisualInspectionApiController {

    @Autowired
    private VisualInspectionService visualInspectionService;

    @PostMapping
    public ResponseEntity<VisualInspection> createVisualInspection(@RequestBody VisualInspection visualInspection) {
        return ResponseEntity.ok(visualInspectionService.saveInspection(visualInspection));
    }

//    @GetMapping("/{bikeId}")
//    public ResponseEntity<VisualInspection> getByBike(@PathVariable Long bikeId) {
//        return visualInspectionService.getByBikeid(bikeId)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
}
