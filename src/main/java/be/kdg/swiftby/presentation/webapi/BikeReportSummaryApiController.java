package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.presentation.webapi.dto.BikeReportSummaryApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.response.BikeReportSummaryDto;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report-summaries")
@RequiredArgsConstructor
public class BikeReportSummaryApiController {
    private final BikeReportSummaryService bikeReportSummaryService;
    private final BikeReportSummaryApiMapper bikeReportSummaryApiMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BikeReportSummaryDto> getSummaryById(@PathVariable Long id) {
        return ResponseEntity.ok(bikeReportSummaryApiMapper.toBikeReportSummaryDto(bikeReportSummaryService.getSummaryById(id)));
    }
}
