package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.presentation.webapi.dto.request.FacilityApiResponseDto;
import be.kdg.swiftby.repository.testEnvironment.FacilityRepository;
import be.kdg.swiftby.service.dto.mapper.FacilityMapper;
import be.kdg.swiftby.service.intf.FacilityService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService {
    FacilityRepository facilityRepository;
    FacilityMapper facilityMapper;

    public FacilityServiceImpl(FacilityRepository facilityRepository, FacilityMapper facilityMapper) {
        this.facilityRepository = facilityRepository;
        this.facilityMapper = facilityMapper;
    }

    @Override
    public List<Facility> getAll() {
        return facilityRepository.findAll();
    }

    @Override
    public Facility getById(Long id) {
        return facilityRepository.findById(id).orElseThrow(() -> NotFoundException.forFacility(id));
    }

    @Override
    public Facility save(FacilityApiResponseDto facilityApiResponseDto) {
        return facilityRepository.save(facilityMapper.toFacility(facilityApiResponseDto));
    }

    @Override
    public void remove(Long id) {
        facilityRepository.deleteById(id);
    }
}
