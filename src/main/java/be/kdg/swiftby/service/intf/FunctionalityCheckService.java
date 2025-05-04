package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.service.dto.FunctionalCheckDTO;

import java.util.UUID;

public interface FunctionalityCheckService {
    FunctionalCheckDTO save(UUID testId,FunctionalCheckDTO dto);

    FunctionalCheckDTO getByTestId(UUID testId);
}
