package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.testEnv.TestBench;
import be.kdg.swiftby.presentation.webapi.dto.TestBenchMapper;
import be.kdg.swiftby.presentation.webapi.dto.request.TestBenchDto;
import be.kdg.swiftby.service.intf.FacilityService;
import be.kdg.swiftby.service.intf.TestBenchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/facilities/{id}")
public class FacilityApiController {
    FacilityService facilityService;
    TestBenchService testBenchService;
    TestBenchMapper testBenchMapper;

    public FacilityApiController(FacilityService facilityService, TestBenchService testBenchService, TestBenchMapper testBenchMapper) {
        this.facilityService = facilityService;
        this.testBenchService = testBenchService;
        this.testBenchMapper = testBenchMapper;
    }

    @GetMapping("/testbenches")
    public ResponseEntity<List<TestBenchDto>> getTestBenches(@PathVariable Long id) {
        List<TestBenchDto> testBenches = testBenchMapper.toTestBenchDtoList(
                testBenchService.getAllByFacilityId(id));
        return ResponseEntity.ok(testBenches);
    }
}
