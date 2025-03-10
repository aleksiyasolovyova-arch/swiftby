package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.presentation.webapi.dto.AdministratorApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.FacilityApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.TechnicianApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.TestBenchApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.response.AdministratorApiResponseDto;
import be.kdg.swiftby.presentation.webapi.dto.response.FacilityApiResponseDto;
import be.kdg.swiftby.presentation.webapi.dto.response.TechnicianApiResponseDto;
import be.kdg.swiftby.presentation.webapi.dto.response.TestBenchApiResponseDto;
import be.kdg.swiftby.service.intf.AdministratorService;
import be.kdg.swiftby.service.intf.FacilityService;
import be.kdg.swiftby.service.intf.TechnicianService;
import be.kdg.swiftby.service.intf.TestBenchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/facilities")
public class FacilityApiController {
    FacilityService facilityService;
    TestBenchService testBenchService;
    TechnicianService technicianService;
    AdministratorService administratorService;

    TestBenchApiMapper testBenchApiMapper;
    FacilityApiMapper facilityApiMapper;

    TechnicianApiMapper technicianApiMapper;
    AdministratorApiMapper administratorApiMapper;

    Logger log = LoggerFactory.getLogger(FacilityApiController.class);

    public FacilityApiController(FacilityService facilityService, TestBenchService testBenchService, TechnicianService technicianService, AdministratorService administratorService, TestBenchApiMapper testBenchApiMapper, FacilityApiMapper facilityApiMapper, TechnicianApiMapper technicianApiMapper, AdministratorApiMapper administratorApiMapper) {
        this.facilityService = facilityService;
        this.testBenchService = testBenchService;
        this.technicianService = technicianService;
        this.administratorService = administratorService;
        this.testBenchApiMapper = testBenchApiMapper;
        this.facilityApiMapper = facilityApiMapper;
        this.technicianApiMapper = technicianApiMapper;
        this.administratorApiMapper = administratorApiMapper;
    }

    //facilities
    @GetMapping("")
    public ResponseEntity<List<FacilityApiResponseDto>> getAllFacilities() {
        try {
            List<FacilityApiResponseDto> facilities = facilityApiMapper.toFacilityApiRequestDtoList(
                    facilityService.getAll());
            return ResponseEntity.ok(facilities);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }

    @GetMapping("{facilityId}")
    public ResponseEntity<FacilityApiResponseDto> getFacilityById(@PathVariable Long facilityId) {
        try {
            FacilityApiResponseDto facility = facilityApiMapper.toFacilityApiRequestDto(
                    facilityService.getById(facilityId));
            return ResponseEntity.ok(facility);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }


    //testbenches
    @GetMapping("{facilityId}/testbenches")
    public ResponseEntity<List<TestBenchApiResponseDto>> getAllTestBenchesByFacilityId(@PathVariable Long facilityId) {
        try {
            List<TestBenchApiResponseDto> testBenches = testBenchApiMapper.toTestBenchDtoList(
                    testBenchService.getAllByFacilityId(facilityId));
            return ResponseEntity.ok(testBenches);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{facilityId}/testbenches/{testBenchId}")
    public ResponseEntity<TestBenchApiResponseDto> getTestBench(@PathVariable Long facilityId, @PathVariable Long testBenchId) {
        //todo check if logged in user has permission
        try {
            log.debug(String.format("Converting facilityId %s and testBenchId %s to testBenchDTO", facilityId, testBenchId));
            TestBenchApiResponseDto testBench = testBenchApiMapper.toTestBenchDto(
                    testBenchService.getByFacilityIdAndTestBenchId(facilityId, testBenchId));
            return ResponseEntity.ok(testBench);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    //technicians
    @GetMapping("{facilityId}/technicians")
    public ResponseEntity<List<TechnicianApiResponseDto>> getAllTechniciansByFacilityId(@PathVariable Long facilityId) {
        try {
            List<TechnicianApiResponseDto> technicians = technicianApiMapper.toTechnicianApiRequestDtoList(
                    technicianService.getAllByFacilityId(facilityId)
            );
            return ResponseEntity.ok(technicians);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("{facilityId}/technicians/{technicianId}")
    public ResponseEntity<TechnicianApiResponseDto> getTechnician(@PathVariable Long facilityId, @PathVariable Long technicianId) {
        try {
            TechnicianApiResponseDto technician = technicianApiMapper.toTechnicianApiRequestDto(
                    //todo fix lazy relation with administrator
                    technicianService.getByFacilityIdAndTechnicianId(facilityId, technicianId)
            );
            return ResponseEntity.ok(technician);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //administrators
    @GetMapping("/{facilityId}/administrators")
    public ResponseEntity<List<AdministratorApiResponseDto>> getAllAdministratorsByFacilityId(@PathVariable Long facilityId) {
        try {
            List<AdministratorApiResponseDto> admins = administratorApiMapper.toAdminDtoList(
                    administratorService.getAllByFacilityId(facilityId));
            log.debug("Found all administrators for facility with id {}", facilityId);
            return ResponseEntity.ok(admins);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{facilityId}/administrators/{administratorId}")
    public ResponseEntity<AdministratorApiResponseDto> getAdministrator(@PathVariable Long facilityId,
                                                                        @PathVariable Long administratorId) {
        try {
            AdministratorApiResponseDto admin = administratorApiMapper.toAdminDto(
                    administratorService.getByFacilityIdAndAdministratorId(facilityId, administratorId));
            log.debug("Found administrator with id {} in facility with id {}: {}",
                    administratorId, facilityId, admin);
            return ResponseEntity.ok(admin);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
