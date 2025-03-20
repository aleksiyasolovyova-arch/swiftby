package be.kdg.swiftby.service.intf;

import java.time.LocalDate;

public interface BikeReportPdfService {


    byte[] generateBikeReportPdf(Long bikeId, LocalDate reportDate);
}
