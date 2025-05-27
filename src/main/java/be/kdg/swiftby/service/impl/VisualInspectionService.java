package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.VisualInspection;

public interface VisualInspectionService {

    VisualInspection saveInspection(VisualInspection visualInspection);

    void saveAndLinkReport(Long testId, VisualInspection inspection);

    VisualInspection findById(Long id);

}
