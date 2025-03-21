package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.repository.report.BikeReportSummaryRepository;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeReportSummaryServiceImpl implements BikeReportSummaryService {
    private final BikeReportSummaryRepository bikeReportSummaryRepository;

    @Override
    public List<BikeReportSummary> getAllSummaries() {
        return bikeReportSummaryRepository.findAll();
    }

    @Override
    public BikeReportSummary getSummaryById(Long id) {
        return bikeReportSummaryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Summary not found"));
    }

    @Override
    public List<BikeReportSummary> getSummariesByBikeId(Long bikeId) {
        return null;
    }

    @Override
    public BikeReportSummary getSummaryByBikeAndDate(Long bikeId, LocalDate reportDate) {
        return bikeReportSummaryRepository.getBikeReportSummary(bikeId, reportDate);
    }
}
