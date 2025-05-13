package be.kdg.swiftby.service.intf;

import be.kdg.swiftby.domain.report.BikeReportSummary;

public interface BikeReportSummaryPdfService {

    byte[] generatePdf(BikeReportSummary summary);
}
