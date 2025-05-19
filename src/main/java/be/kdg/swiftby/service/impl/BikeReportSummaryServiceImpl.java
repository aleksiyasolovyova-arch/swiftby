package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.exception.NotFoundException;
import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.report.FunctionalityCheck;
import be.kdg.swiftby.repository.report.BikeReportSummaryRepository;
import be.kdg.swiftby.repository.report.FunctionalityCheckRepository;
import be.kdg.swiftby.service.dto.BikeReportChartDto;
import be.kdg.swiftby.service.dto.data.BatteryTestDto;
import be.kdg.swiftby.service.dto.data.NominalLoadTestDto;
import be.kdg.swiftby.service.dto.data.TestProcedureOverviewDto;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BikeReportSummaryServiceImpl implements BikeReportSummaryService {
    private final BikeReportSummaryRepository bikeReportSummaryRepository;
    private final FunctionalityCheckRepository functionalityCheckRepository;

    @Override
    public List<BikeReportSummary> getAllSummaries() {
        return bikeReportSummaryRepository.findAllByOrderByReportTimeDesc();
    }

    @Override
    public BikeReportSummary getSummaryById(Long id) {
        return bikeReportSummaryRepository.findByIdWithBike(id)
                .orElseThrow(() -> NotFoundException.forBikeReport(id));
    }

    @Override
    public List<BikeReportSummary> getSummariesByBikeId(Long bikeId) {
        return bikeReportSummaryRepository.findByBikeInstanceIdOrderByReportTimeDesc(bikeId);
    }

    @Override
    public BikeReportSummary getSummaryByBikeAndDate(Long bikeId, LocalDate reportDate) {
        return bikeReportSummaryRepository.findByBikeInstanceIdOrderByReportTimeDesc(bikeId).stream()
                .filter(s -> s.getReportTime() != null && s.getReportTime().equals(reportDate))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Summary not found"));
    }





    @Override
    public List<BikeReportChartDto> getChartDataWithInterval(Long summaryId, String mode, int intervalSeconds) {
        BikeReportSummary summary = bikeReportSummaryRepository.findByIdWithReports(summaryId)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        List<BikeReport> reports = summary.getReports().stream()
                .sorted(Comparator.comparing(BikeReport::getReportTime))
                .toList();

        if (reports.isEmpty()) return List.of();

        long startTime = reports.get(0).getReportTime().toEpochSecond(ZoneOffset.UTC);

        Map<Long, List<BikeReport>> grouped = reports.stream()
                .collect(Collectors.groupingBy(report ->
                        (report.getReportTime().toEpochSecond(ZoneOffset.UTC) - startTime) / intervalSeconds
                ));

        List<BikeReportChartDto> chartData = grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    List<BikeReport> group = entry.getValue();
                    long time = entry.getKey() * intervalSeconds;

                    double voltage = group.stream().mapToDouble(r -> r.getBatteryData() != null ? r.getBatteryData().getVoltage() : 0).average().orElse(0);
                    double current = group.stream().mapToDouble(r -> r.getBatteryData() != null ? r.getBatteryData().getBatteryCurrent() : 0).average().orElse(0);
                    double temperature = group.stream().mapToDouble(r -> r.getBatteryData() != null ? r.getBatteryData().getTemperature() : 0).average().orElse(0);
                    double enginePower = group.stream().mapToDouble(r -> r.getMotorData() != null ? r.getMotorData().getEnginePower() : 0).average().orElse(0);
                    double wheelPower = group.stream().mapToDouble(r -> r.getWheelData() != null ? r.getWheelData().getPower() : 0).average().orElse(0);
                    double torqueCrank = group.stream().mapToDouble(r -> r.getPedalData() != null ? r.getPedalData().getTorqueCrank() : 0).average().orElse(0);
                    double rollerTorque = group.stream().mapToDouble(r -> r.getTestBenchData() != null ? r.getTestBenchData().getRollerTorque() : 0).average().orElse(0);
                    double cadence = group.stream().mapToDouble(r -> r.getPedalData() != null ? r.getPedalData().getCadence() : 0).average().orElse(0);
                    double speed = group.stream().mapToDouble(r -> r.getWheelData() != null ? r.getWheelData().getSpeed() : 0).average().orElse(0);
                    double horizIncl = group.stream().mapToDouble(r -> r.getAxialSensorData() != null ? r.getAxialSensorData().getHorizontalInclination() : 0).average().orElse(0);
                    double vertIncl = group.stream().mapToDouble(r -> r.getAxialSensorData() != null ? r.getAxialSensorData().getVerticalInclination() : 0).average().orElse(0);

                    return new BikeReportChartDto(voltage, current, temperature, enginePower, wheelPower,
                            torqueCrank, rollerTorque, cadence, speed, horizIncl, vertIncl, time);
                })
                .toList();

        if (!mode.equalsIgnoreCase("normalized")) return chartData;

        // Normalize all metrics using their respective maximums
        double maxVoltage = chartData.stream().mapToDouble(BikeReportChartDto::batteryVoltage).max().orElse(1);
        double maxCurrent = chartData.stream().mapToDouble(BikeReportChartDto::batteryCurrent).max().orElse(1);
        double maxTemperature = chartData.stream().mapToDouble(BikeReportChartDto::batteryTemperature).max().orElse(1);
        double maxEnginePower = chartData.stream().mapToDouble(BikeReportChartDto::enginePower).max().orElse(1);
        double maxWheelPower = chartData.stream().mapToDouble(BikeReportChartDto::wheelPower).max().orElse(1);
        double maxTorqueCrank = chartData.stream().mapToDouble(BikeReportChartDto::torqueCrank).max().orElse(1);
        double maxRollerTorque = chartData.stream().mapToDouble(BikeReportChartDto::rollerTorque).max().orElse(1);
        double maxCadence = chartData.stream().mapToDouble(BikeReportChartDto::cadence).max().orElse(1);
        double maxSpeed = chartData.stream().mapToDouble(BikeReportChartDto::speed).max().orElse(1);
        double maxHorizInclination = chartData.stream().mapToDouble(BikeReportChartDto::horizontalInclination).max().orElse(1);
        double maxVertInclination = chartData.stream().mapToDouble(BikeReportChartDto::verticalInclination).max().orElse(1);

        return chartData.stream()
                .map(d -> new BikeReportChartDto(
                        d.batteryVoltage() / maxVoltage,
                        d.batteryCurrent() / maxCurrent,
                        d.batteryTemperature() / maxTemperature,
                        d.enginePower() / maxEnginePower,
                        d.wheelPower() / maxWheelPower,
                        d.torqueCrank() / maxTorqueCrank,
                        d.rollerTorque() / maxRollerTorque,
                        d.cadence() / maxCadence,
                        d.speed() / maxSpeed,
                        d.horizontalInclination() / maxHorizInclination,
                        d.verticalInclination() / maxVertInclination,
                        d.time()
                ))
                .toList();
    }


    @Override
    public TestProcedureOverviewDto getTestProcedureOverview(Long summaryId) {
        return bikeReportSummaryRepository.getTestProcedureOverview(summaryId)
                .orElseThrow(() -> new RuntimeException("Overview not found"));
    }



    public BikeReportSummary getSummaryWithCheck(Long id) {
        return bikeReportSummaryRepository.findByIdWithCheck(id)
                .orElseThrow(() -> new RuntimeException("Summary not found"));
    }


    @Override
    public NominalLoadTestDto getNominalLoadTest(Long summaryId) {
        BikeReportSummary summary = bikeReportSummaryRepository.findByIdWithReports(summaryId)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        var reports = summary.getReports();

        double totalEnginePower = 0;
        int enginePowerCount = 0;

        double minTemp = Double.MAX_VALUE;
        double maxTemp = Double.MIN_VALUE;

        for (var report : reports) {
            var motorData = report.getMotorData();
            var batteryData = report.getBatteryData();

            if (motorData != null) {
                totalEnginePower += motorData.getEnginePower();
                enginePowerCount++;
            }

            if (batteryData != null) {
                double temp = batteryData.getTemperature();
                if (temp < minTemp) minTemp = temp;
                if (temp > maxTemp) maxTemp = temp;
            }
        }

        double avgEnginePower = enginePowerCount > 0 ? totalEnginePower / enginePowerCount : 0;
        double temperatureIncrease = (minTemp < maxTemp) ? (maxTemp - minTemp) : 0;

        return new NominalLoadTestDto(avgEnginePower, temperatureIncrease);
    }


    @Override
    public BatteryTestDto getBatteryTest(Long summaryId) {
        return bikeReportSummaryRepository.getBatteryTestData(summaryId)
                .orElseThrow(() -> new RuntimeException("Battery test data not found or battery not fully charged"));
    }


    private String calculateBearingHealth(List<BikeReport> reports, double horizontalThreshold, double verticalThreshold) {
        double minHorizontal = reports.stream()
                .mapToDouble(r -> r.getAxialSensorData().getHorizontalInclination())
                .min().orElse(0);

        double maxHorizontal = reports.stream()
                .mapToDouble(r -> r.getAxialSensorData().getHorizontalInclination())
                .max().orElse(0);

        double minVertical = reports.stream()
                .mapToDouble(r -> r.getAxialSensorData().getVerticalInclination())
                .min().orElse(0);

        double maxVertical = reports.stream()
                .mapToDouble(r -> r.getAxialSensorData().getVerticalInclination())
                .max().orElse(0);

        boolean isGood = (maxHorizontal - minHorizontal) <= horizontalThreshold
                && (maxVertical - minVertical) <= verticalThreshold;

        return isGood ? "good" : "bad";
    }


    @Override
    public String evaluateAndStoreBearingHealth(Long summaryId, double horizontalThreshold, double verticalThreshold) {
        BikeReportSummary summary = bikeReportSummaryRepository.findByIdWithReports(summaryId)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        String result = calculateBearingHealth(summary.getReports(), horizontalThreshold, verticalThreshold);
        summary.setBearingHealth(result);
        bikeReportSummaryRepository.save(summary);

        return result;
    }



    @Override
    public void attachFunctionalityCheck(Long summaryId, Long checkId) {
        BikeReportSummary summary = bikeReportSummaryRepository.findById(summaryId)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        FunctionalityCheck check = functionalityCheckRepository.findById(checkId)
                .orElseThrow(() -> new RuntimeException("Functional check not found"));

        summary.setFunctionalityCheck(check);
        bikeReportSummaryRepository.save(summary);
    }

    @Override
    public List<BikeReportSummary> getSummariesByBikeInstanceId(Long bikeInstanceId) {
        return bikeReportSummaryRepository.findAllByBikeInstanceId(bikeInstanceId);
    }

    @Override
    public List<BikeReportSummary> getAllSummariesByBikeOwnerId(Long bikeOwnerId) {
        return bikeReportSummaryRepository.findAllBikeReportSummariesByBikeOwnerId(bikeOwnerId);
    }



}
