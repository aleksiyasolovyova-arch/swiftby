package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.FunctionalityCheck;

public interface FunctionalityCheckService {

    FunctionalityCheck save(FunctionalityCheck check);

    FunctionalityCheck findById(Long id);

}
