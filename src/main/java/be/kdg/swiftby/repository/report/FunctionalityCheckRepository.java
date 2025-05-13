package be.kdg.swiftby.repository.report;
import be.kdg.swiftby.domain.report.FunctionalityCheck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FunctionalityCheckRepository extends JpaRepository<FunctionalityCheck,Long > {


}

