package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.FunctionalityCheck;
import be.kdg.swiftby.repository.report.FunctionalityCheckRepository;
import be.kdg.swiftby.service.intf.FunctionalityCheckService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FunctionalityCheckServiceImpl implements FunctionalityCheckService {

    private final FunctionalityCheckRepository repository;

    @Override
    public FunctionalityCheck save(FunctionalityCheck check) {
        return repository.save(check);
    }

    @Override
    public FunctionalityCheck findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Functional check not found"));
    }


}