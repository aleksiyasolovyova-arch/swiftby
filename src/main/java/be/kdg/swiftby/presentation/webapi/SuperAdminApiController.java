package be.kdg.swiftby.presentation.webapi;

import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.presentation.webapi.dto.SuperAdminApiMapper;
import be.kdg.swiftby.presentation.webapi.dto.response.SuperAdminApiRequestDto;
import be.kdg.swiftby.service.intf.SuperAdminService;
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
@RequestMapping("api/superadmins")
public class SuperAdminApiController {
    SuperAdminService superAdminService;
    SuperAdminApiMapper superAdminApiMapper;

    Logger log = LoggerFactory.getLogger(SuperAdminApiController.class);

    public SuperAdminApiController(SuperAdminService superAdminService, SuperAdminApiMapper superAdminApiMapper) {
        this.superAdminService = superAdminService;
        this.superAdminApiMapper = superAdminApiMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<SuperAdminApiRequestDto>> getAllSuperAdmins() {
        List<SuperAdminApiRequestDto> superAdmins = superAdminApiMapper.toSuperAdminApiRequestDtoList(superAdminService.getAll());
        log.debug("SuperAdmins: {}", superAdmins);
        return ResponseEntity.ok(superAdmins);
    }

    @GetMapping("{id}")
    public ResponseEntity<SuperAdminApiRequestDto> getSuperAdmin(@PathVariable Long id) {
        try {
            SuperAdminApiRequestDto superAdmin = superAdminApiMapper.toSuperAdminApiRequestDto(superAdminService.getById(id));
            return ResponseEntity.ok(superAdmin);
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
