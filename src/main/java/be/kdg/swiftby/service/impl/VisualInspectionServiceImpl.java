package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.report.VisualInspection;
import be.kdg.swiftby.repository.report.BikeReportSummaryRepository;
import be.kdg.swiftby.repository.report.VisualInspectionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class VisualInspectionServiceImpl implements VisualInspectionService {

    VisualInspectionRepository visualInspectionRepository;
    BikeReportSummaryRepository bikeReportSummaryRepository;

    public VisualInspectionServiceImpl(VisualInspectionRepository visualInspectionRepository, BikeReportSummaryRepository bikeReportSummaryRepository) {
        this.visualInspectionRepository = visualInspectionRepository;
        this.bikeReportSummaryRepository = bikeReportSummaryRepository;
    }

    public VisualInspection saveInspection(VisualInspection visualInspection) {
        return visualInspectionRepository.save(visualInspection);
    }

    @Override
    public void saveAndLinkReport(Long testId, VisualInspection inspection) {
        VisualInspection savedInspection = saveInspection(inspection);

        bikeReportSummaryRepository.findById(testId).ifPresent(summary -> {
            summary.setVisualInspection(savedInspection);
            bikeReportSummaryRepository.save(summary);
        });
    }

    @Override
    public VisualInspection findById(Long id) {
        return visualInspectionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Visual inspection not found"));
    }

}
