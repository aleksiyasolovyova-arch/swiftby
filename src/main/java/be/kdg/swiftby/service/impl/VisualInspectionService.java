package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.VisualInspection;
import be.kdg.swiftby.repository.report.VisualInspectionRepository;
import org.springframework.stereotype.Service;


@Service
public class VisualInspectionService {

    VisualInspectionRepository visualInspectionRepository;

    public VisualInspectionService(VisualInspectionRepository visualInspectionRepository) {
        this.visualInspectionRepository = visualInspectionRepository;
    }

    public VisualInspection saveInspection(VisualInspection visualInspection) {
        return visualInspectionRepository.save(visualInspection);
    }


}
