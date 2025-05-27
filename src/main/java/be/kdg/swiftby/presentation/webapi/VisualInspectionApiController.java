package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.report.VisualInspection;
import be.kdg.swiftby.presentation.webapi.dto.VisualInspectionDto;
import be.kdg.swiftby.presentation.webapi.dto.VisualInspectionMapper;
import be.kdg.swiftby.service.impl.VisualInspectionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visual-inspections")
public class VisualInspectionApiController {

    @Autowired
    private VisualInspectionServiceImpl visualInspectionService;

    @Autowired
    private VisualInspectionMapper mapper;

    @PostMapping
    public ResponseEntity<Long> createVisualInspection(@RequestBody VisualInspectionDto dto) {
        var entity = mapper.toEntity(dto);
        var saved = visualInspectionService.saveInspection(entity);
        return ResponseEntity.ok(saved.getId());
    }

    @PostMapping("/{summaryId}")
    public ResponseEntity<Long> createAndLinkVisualInspection(
            @PathVariable Long summaryId,
            @RequestBody VisualInspectionDto dto) {
        var entity = mapper.toEntity(dto);
        visualInspectionService.saveAndLinkReport(summaryId, entity);
        return ResponseEntity.ok(entity.getId());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VisualInspectionDto> getVisualInspection(@PathVariable Long id) {
        VisualInspection inspection = visualInspectionService.findById(id);

        VisualInspectionDto dto = new VisualInspectionDto(
                inspection.getTires(),
                inspection.getCranks(),
                inspection.getElectricalWiring(),
                inspection.getFrameFork(),
                inspection.getGrips(),
                inspection.getChainBelt(),
                inspection.getPedals(),
                inspection.getReflectors(),
                inspection.getBrakePads(),
                inspection.getBrakeLevers(),
                inspection.getBrakeCables(),
                inspection.getBrakeDiscs(),
                inspection.getGearCables(),
                inspection.getMudguards(),
                inspection.getHandlebarStem(),
                inspection.getRearSprocket(),
                inspection.getFrontSprocket(),
                inspection.getRimSpokes(),
                inspection.getRearSuspension(),
                inspection.getFrontSuspension(),
                inspection.getSaddle()
        );
        return ResponseEntity.ok(dto);
    }
}
