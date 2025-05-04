package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.FunctionalityCheck;
import be.kdg.swiftby.service.dto.FunctionalCheckDTO;

import java.util.UUID;

public interface FunctionalityCheckService {
    FunctionalityCheck save(FunctionalCheckDTO dto);

//    FunctionalCheckDTO getByTestId(UUID testId);
}
