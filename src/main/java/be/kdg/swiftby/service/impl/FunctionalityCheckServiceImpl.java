package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.FunctionalityCheck;
import be.kdg.swiftby.presentation.webapi.dto.FunctionalityCheckApiMapper;
import be.kdg.swiftby.repository.report.FunctionalityCheckRepository;
import be.kdg.swiftby.service.dto.FunctionalCheckDTO;
import be.kdg.swiftby.service.intf.FunctionalityCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FunctionalityCheckServiceImpl implements FunctionalityCheckService {

    private final FunctionalityCheckRepository repository;

    private final FunctionalityCheckApiMapper mapper;

    @Override
    public FunctionalityCheck save(FunctionalCheckDTO dto) {
        FunctionalityCheck entity = mapper.toEntity(dto);
        return repository.save(entity);
    }

//    @Override
//    public FunctionalCheckDTO getByTestId(UUID testId) {
//        FunctionalityCheck entity = repository.findByTestId(testId)
//                .orElseThrow(() -> new RuntimeException("Functional check not found for testId: " + testId));
//
//        return mapper.toDto(entity);
//    }
}
