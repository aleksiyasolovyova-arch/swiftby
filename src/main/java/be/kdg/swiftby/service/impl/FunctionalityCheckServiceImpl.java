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

    @Qualifier("functionalityCheckApiMapper")
    private final FunctionalityCheckApiMapper mapper;

    @Override
    public FunctionalCheckDTO save(UUID testId,FunctionalCheckDTO dto) {
        // Map the DTO to an Entity
        FunctionalityCheck entity = mapper.toEntity(dto);

        // Save the entity to the database
        FunctionalityCheck saved = repository.save(entity);

        // Map back to DTO and return
        return mapper.toDto(saved);
    }

    @Override
    public FunctionalCheckDTO getByTestId(UUID testId) {
        FunctionalityCheck entity = repository.findByTestId(testId)
                .orElseThrow(() -> new RuntimeException("Functional check not found for testId: " + testId));

        return mapper.toDto(entity);
    }
}
