package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.report.FunctionalityCheck;
import be.kdg.swiftby.repository.report.BikeReportSummaryRepository;
import be.kdg.swiftby.repository.report.FunctionalityCheckRepository;
import be.kdg.swiftby.service.dto.BearingHealthEvaluation;
import be.kdg.swiftby.service.dto.BikeReportChartDto;
import be.kdg.swiftby.service.dto.ReportChartSeriesDto;
import be.kdg.swiftby.service.dto.ServiceSummaryIdDateDto;
import be.kdg.swiftby.service.dto.data.BatteryTestDto;
import be.kdg.swiftby.service.dto.data.NominalLoadTestDto;
import be.kdg.swiftby.service.dto.data.TestProcedureOverviewDto;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.*;
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
                .orElseThrow(() -> new RuntimeException("Summary not found"));
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



    @Override
    public BearingHealthEvaluation evaluateBearingHealth(Long summaryId) {
        BikeReportSummary summary = bikeReportSummaryRepository.findByIdWithReports(summaryId)
                .orElseThrow(() -> new RuntimeException("Summary not found"));

        double horizontalThreshold = summary.getHorizontalInclination();
        double verticalThreshold = summary.getVerticalInclination();

        List<BikeReport> reports = summary.getReports();

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

        double horizontalRange = maxHorizontal - minHorizontal;
        double verticalRange = maxVertical - minVertical;

        boolean isBad = horizontalRange > horizontalThreshold || verticalRange > verticalThreshold;
        String result = isBad ? "bad" : "good";

        summary.setBearingHealth(result);
        bikeReportSummaryRepository.save(summary);

        return new BearingHealthEvaluation(horizontalRange, verticalRange, isBad);
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
    public List<ReportChartSeriesDto> getFieldOverTimeForTwoReports(Long summary1Id, Long summary2Id, String field, int intervalSeconds) {
        BikeReportSummary s1 = bikeReportSummaryRepository.findByIdWithReports(summary1Id)
                .orElseThrow(() -> new RuntimeException("Summary 1 not found"));
        BikeReportSummary s2 = bikeReportSummaryRepository.findByIdWithReports(summary2Id)
                .orElseThrow(() -> new RuntimeException("Summary 2 not found"));

        return List.of(
                buildFieldSeriesFromReportsWithInterval(s1, field, intervalSeconds),
                buildFieldSeriesFromReportsWithInterval(s2, field, intervalSeconds)
        );
    }

    @Override
    public ReportChartSeriesDto buildFieldSeriesFromReportsWithInterval(BikeReportSummary summary, String field, int intervalSeconds) {
        List<BikeReport> reports = summary.getReports().stream()
                .sorted(Comparator.comparing(BikeReport::getReportTime))
                .toList();

        if (reports.isEmpty()) {
            return new ReportChartSeriesDto("Report " + summary.getId(), List.of(), List.of());
        }

        long startTime = reports.get(0).getReportTime().toEpochSecond(ZoneOffset.UTC);

        Map<Long, List<BikeReport>> grouped = reports.stream()
                .collect(Collectors.groupingBy(report ->
                        (report.getReportTime().toEpochSecond(ZoneOffset.UTC) - startTime) / intervalSeconds
                ));

        List<Double> values = new ArrayList<>();
        List<String> timeLabels = new ArrayList<>();

        boolean useSummaryField = field.equals("max_power") || field.equals("rol");
        double summaryValue = switch (field) {
            case "max_power" -> summary.getMaxPower() != null ? summary.getMaxPower() : 0;
            case "rol" -> summary.getRol();
            default -> -1;
        };

        grouped.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    long time = entry.getKey() * intervalSeconds;
                    List<BikeReport> group = entry.getValue();

                    double value;
                    if (useSummaryField) {
                        value = summaryValue;
                    } else {
                        value = group.stream().mapToDouble(report -> switch (field) {
                            case "cadence" -> report.getPedalData() != null ? report.getPedalData().getCadence() : 0;
                            case "current" -> report.getBatteryData() != null ? report.getBatteryData().getBatteryCurrent() : 0;
                            case "voltage" -> report.getBatteryData() != null ? report.getBatteryData().getVoltage() : 0;
                            case "temperature" -> report.getBatteryData() != null ? report.getBatteryData().getTemperature() : 0;
                            case "capacity" -> report.getBatteryData() != null ? report.getBatteryData().getCapacity() : 0;
                            case "engine_power" -> report.getMotorData() != null ? report.getMotorData().getEnginePower() : 0;
                            case "power", "load_power" -> report.getWheelData() != null ? report.getWheelData().getPower() : 0;
                            case "speed" -> report.getWheelData() != null ? report.getWheelData().getSpeed() : 0;
                            case "torque" -> report.getPedalData() != null ? report.getPedalData().getTorqueCrank() : 0;
                            case "load_cell" -> report.getTestBenchData() != null ? report.getTestBenchData().getLoadCell() : 0;
                            default -> 0;
                        }).average().orElse(0);
                    }

                    values.add(value);
                    timeLabels.add(formatSecondsToTimeLabel(time));
                });

        return new ReportChartSeriesDto("Report " + summary.getId(), values, timeLabels);
    }

    private String formatSecondsToTimeLabel(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }





    @Override
    public ReportChartSeriesDto buildFieldSeriesFromReports(BikeReportSummary summary, String field) {
        List<BikeReport> reports = summary.getReports().stream()
                .sorted(Comparator.comparing(BikeReport::getReportTime))
                .toList();

        if (reports.isEmpty()) {
            return new ReportChartSeriesDto("Report " + summary.getId(), List.of(), List.of());
        }

        List<Double> values = new ArrayList<>();
        List<String> timeLabels = new ArrayList<>();

        boolean useSummaryField = field.equals("max_power") || field.equals("rol");

        double summaryValue = switch (field) {
            case "max_power" -> summary.getMaxPower() != null ? summary.getMaxPower() : 0;
            case "rol" -> summary.getRol();
            default -> -1;
        };

        for (BikeReport report : reports) {
            double value = useSummaryField ? summaryValue : switch (field) {
                case "cadence" -> report.getPedalData() != null ? report.getPedalData().getCadence() : 0;
                case "current" -> report.getBatteryData() != null ? report.getBatteryData().getBatteryCurrent() : 0;
                case "voltage" -> report.getBatteryData() != null ? report.getBatteryData().getVoltage() : 0;
                case "temperature" -> report.getBatteryData() != null ? report.getBatteryData().getTemperature() : 0;
                case "capacity" -> report.getBatteryData() != null ? report.getBatteryData().getCapacity() : 0;
                case "engine_power" -> report.getMotorData() != null ? report.getMotorData().getEnginePower() : 0;
                case "power", "load_power" -> report.getWheelData() != null ? report.getWheelData().getPower() : 0;
                case "speed" -> report.getWheelData() != null ? report.getWheelData().getSpeed() : 0;
                case "torque" -> report.getPedalData() != null ? report.getPedalData().getTorqueCrank() : 0;
                case "load_cell" -> report.getTestBenchData() != null ? report.getTestBenchData().getLoadCell() : 0;
                default -> 0;
            };

            values.add(value);
            timeLabels.add(report.getReportTime().toLocalTime().toString());
        }

        return new ReportChartSeriesDto("Report " + summary.getId(), values, timeLabels);
    }







    @Override
    public List<ReportChartSeriesDto> compareSummaryFields(Long summary1Id, Long summary2Id) {
        BikeReportSummary s1 = getSummaryById(summary1Id);
        BikeReportSummary s2 = getSummaryById(summary2Id);

        List<ReportChartSeriesDto> results = new ArrayList<>();

        results.add(makeFieldComparison("cadence", s1.getCadence(), s2.getCadence(), s1, s2));
        results.add(makeFieldComparison("capacity", s1.getCapacity(), s2.getCapacity(), s1, s2));
        results.add(makeFieldComparison("current", s1.getBatteryCurrent(), s2.getBatteryCurrent(), s1, s2));
        results.add(makeFieldComparison("engine_power", s1.getEnginePower(), s2.getEnginePower(), s1, s2));
        results.add(makeFieldComparison("load_cell", s1.getLoadCell(), s2.getLoadCell(), s1, s2));
        results.add(makeFieldComparison("load_power", (double) s1.getLoadPower(), (double) s2.getLoadPower(), s1, s2));
        results.add(makeFieldComparison("max_power", s1.getMaxPower().doubleValue(), s2.getMaxPower().doubleValue(), s1, s2));
        results.add(makeFieldComparison("power", s1.getPower(), s2.getPower(), s1, s2));
        results.add(makeFieldComparison("temperature", s1.getTemperature(), s2.getTemperature(), s1, s2));
        results.add(makeFieldComparison("torque", s1.getTorque().doubleValue(), s2.getTorque().doubleValue(), s1, s2));
        results.add(makeFieldComparison("voltage", s1.getVoltage(), s2.getVoltage(), s1, s2));
        results.add(makeFieldComparison("speed", s1.getSpeed(), s2.getSpeed(), s1, s2));

        return results;
    }

    private ReportChartSeriesDto makeFieldComparison(String fieldName, Double v1, Double v2, BikeReportSummary s1, BikeReportSummary s2) {
        return new ReportChartSeriesDto(
                fieldName,
                List.of(v1, v2),
                List.of("Report " + s1.getId(), "Report " + s2.getId())

        );
    }


//meoww
    @Override
    public List<ServiceSummaryIdDateDto> getAvailableComparisons(Long summaryId) {
        BikeReportSummary current = getSummaryById(summaryId);
        Long bikeId = current.getBikeInstance().getId();

        return getSummariesByBikeId(bikeId).stream()
                .filter(s -> !s.getId().equals(summaryId))
                .map(s -> new ServiceSummaryIdDateDto(s.getId(),s.getReportTime() != null ? s.getReportTime().toString() : "Unknown"))
                .toList();
    }










}
