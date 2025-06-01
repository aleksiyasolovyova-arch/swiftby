package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.bike.BikeModel;
import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.presentation.viewmodels.EmployeeCreateViewModel;
import be.kdg.swiftby.presentation.viewmodels.EmployeeUpdateViewModel;
import be.kdg.swiftby.presentation.webapi.dto.*;
import be.kdg.swiftby.presentation.webapi.dto.bikereport.BikeMapperApi;
import be.kdg.swiftby.presentation.webapi.dto.response.*;
import be.kdg.swiftby.service.intf.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/facilities")
public class FacilityApiController {
   private final FacilityService facilityService;
   private final TestBenchService testBenchService;
   private final TechnicianService technicianService;
   private final AdministratorService administratorService;
   private final TestBenchApiMapper testBenchApiMapper;
   private final FacilityApiMapper facilityApiMapper;
   private final TechnicianApiMapper technicianApiMapper;
   private final AdministratorApiMapper administratorApiMapper;
    private final BikeInstanceService bikeInstanceService;

    private final Logger log = LoggerFactory.getLogger(FacilityApiController.class);
   private final BikeMapperApi bikeApiMapper;
    private final BikeOwnerApiMapper bikeOwnerApiMapper;
    private final BikeOwnerService bikeOwnerService;

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

    @PostMapping("{facilityId}/technicians")
    public ResponseEntity<TechnicianApiResponseDto> createTechnician(@PathVariable Long facilityId,
                                                                     @Valid @RequestBody EmployeeCreateViewModel employeeCreateViewModel) {
        TechnicianApiResponseDto technician = technicianApiMapper.toTechnicianApiRequestDto(
        technicianService.create(facilityId, employeeCreateViewModel.getEmail(),
                employeeCreateViewModel.getPassword(), employeeCreateViewModel.getFirstName(),
                employeeCreateViewModel.getLastName(), employeeCreateViewModel.getPhoneNumber())
        );

        return new ResponseEntity<>(technician, HttpStatus.CREATED);
    }

    @PatchMapping("{facilityId}/technicians/{technicianId}")
    public ResponseEntity<TechnicianApiResponseDto> updateTechnician(@PathVariable Long facilityId,
                                                                    @PathVariable Long technicianId,
                                                                    @Valid @RequestBody EmployeeUpdateViewModel employeeUpdateViewModel) {
        TechnicianApiResponseDto technician = technicianApiMapper.toTechnicianApiRequestDto(
                technicianService.update(facilityId, technicianId, employeeUpdateViewModel.getEmail(),
                        employeeUpdateViewModel.getPassword(), employeeUpdateViewModel.getFirstName(), employeeUpdateViewModel.getLastName(),
                        employeeUpdateViewModel.getPhoneNumber(), employeeUpdateViewModel.getFacilityId()
                ));
        log.debug("Updated administrator with id {} in facility with id {}",
                technicianId, facilityId);
        return ResponseEntity.ok(technician);
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

    @PostMapping("/{facilityId}/administrators")
    public ResponseEntity<AdministratorApiResponseDto> createAdministrator(@PathVariable Long facilityId,
                                                                           @Valid @RequestBody EmployeeCreateViewModel employeeViewModel) {
        AdministratorApiResponseDto admin = administratorApiMapper.toAdminDto(
                administratorService.create(facilityId, employeeViewModel.getEmail(),
                        employeeViewModel.getPassword(), employeeViewModel.getFirstName(), employeeViewModel.getLastName(),
                        employeeViewModel.getPhoneNumber()
                ));
        log.debug("Updated administrator with id {} in facility with id {}",
                admin.id(), facilityId);
        return new ResponseEntity<>(admin ,HttpStatus.CREATED);
    }

    @PatchMapping("/{facilityId}/administrators/{administratorId}")
    public ResponseEntity<AdministratorApiResponseDto> updateAdministrator(@PathVariable Long facilityId,
                                                                           @PathVariable Long administratorId,
                                                                           @Valid @RequestBody EmployeeUpdateViewModel employeeUpdateViewModel) {
            AdministratorApiResponseDto admin = administratorApiMapper.toAdminDto(
                    administratorService.update(facilityId, administratorId, employeeUpdateViewModel.getEmail(),
                            employeeUpdateViewModel.getPassword(), employeeUpdateViewModel.getFirstName(), employeeUpdateViewModel.getLastName(),
                            employeeUpdateViewModel.getPhoneNumber(), employeeUpdateViewModel.getFacilityId()
                            ));
            log.debug("Updated administrator with id {} in facility with id {}",
                    administratorId, facilityId);
            return ResponseEntity.ok(admin);
    }

    // TODO: MOVE TO A TECHNICIAN CONTROLLER SINCE IT'S NOT FACILITY SPECIFIC
    @DeleteMapping("/technicians/{technicianId}")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long technicianId) {
        technicianService.remove(technicianId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{facilityId}/bikes")
    public ResponseEntity<List<BikeApiResponseDto>> getAllBikeInstancesByFacilityId(@PathVariable Long facilityId) {
        List<BikeInstance> bikeInstances = bikeInstanceService.getAllByFacilityId(facilityId);
        if (bikeInstances.isEmpty()) {
            log.warn("No bike instances found for facility ID {}", facilityId);
            return ResponseEntity.noContent().build();
        }
        List<BikeApiResponseDto> response = bikeApiMapper.toBikeInstanceDtoList(bikeInstances);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bikes")
    public ResponseEntity<List<BikeApiResponseDto>> getAllBikeInstances() {
        List<BikeInstance> bikeInstances = bikeInstanceService.getAll();
        if (bikeInstances.isEmpty()) {
            log.warn("No bike instances found");
            return ResponseEntity.noContent().build();
        }
        List<BikeApiResponseDto> response = bikeApiMapper.toBikeInstanceDtoList(bikeInstances);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{facilityId}/bikeowners")
    public ResponseEntity<List<BikeOwnerApiResponseDto>> getAllBikeOwnersByFacilityId(@PathVariable Long facilityId) {
        try{
            List<BikeOwnerApiResponseDto> owners = bikeOwnerApiMapper.toBikeOwnerDtoList(
              bikeOwnerService.getAllByFacilityId(facilityId)
            );
            return ResponseEntity.ok(owners);
        }catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{facilityId}/bikeowners/{ownerId}")
    public ResponseEntity<BikeOwnerApiResponseDto> getBikeOwner(@PathVariable Long ownerId, @PathVariable Long facilityId) {
        try{
            BikeOwnerApiResponseDto owner = bikeOwnerApiMapper.toBikeOwnerDto(
                    bikeOwnerService.getByFacilityIdAndBikeOwnerId(facilityId, ownerId)
            );
            return ResponseEntity.ok(owner);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //TODO:Fix up the update method so it's not a direct copy past from admin and add delete
    @PatchMapping("/{facilityId}/bikeowners/{ownerId}")
    public ResponseEntity<BikeOwnerApiResponseDto> updateBikeOwner(@PathVariable Long facilityId,
                                                                           @PathVariable Long ownerId,
                                                                           @Valid @RequestBody EmployeeUpdateViewModel employeeUpdateViewModel) {
        BikeOwnerApiResponseDto owner = bikeOwnerApiMapper.toBikeOwnerDto(
                bikeOwnerService.update(facilityId, ownerId, employeeUpdateViewModel.getEmail(),
                        employeeUpdateViewModel.getPassword(), employeeUpdateViewModel.getFirstName(), employeeUpdateViewModel.getLastName(),
                        employeeUpdateViewModel.getPhoneNumber(), employeeUpdateViewModel.getFacilityId()
                ));
        log.debug("Updated bike owner with id {} in facility with id {}",
                ownerId, facilityId);
        return ResponseEntity.ok(owner);
    }

}
