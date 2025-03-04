package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.presentation.webapi.dto.request.FacilityApiResponseDto;

import java.util.List;

public interface FacilityService {
    List<Facility> getAll();
    Facility getById(Long id);
    Facility save(FacilityApiResponseDto facilityApiResponseDto);
    void remove(Long id);
}
