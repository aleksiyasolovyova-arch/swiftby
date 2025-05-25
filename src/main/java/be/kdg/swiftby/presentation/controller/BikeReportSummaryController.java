package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.email.EmailService;
import be.kdg.swiftby.service.intf.BikeReportSummaryPdfService;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BikeReportSummaryController {

    private final EmailService emailService;
    private final BikeReportSummaryPdfService bikeReportSummaryPdfService;
    private final BikeReportSummaryService bikeReportSummaryService;

    public BikeReportSummaryController(EmailService emailService, BikeReportSummaryPdfService bikeReportSummaryPdfService, BikeReportSummaryService bikeReportSummaryService) {
        this.emailService = emailService;
        this.bikeReportSummaryPdfService = bikeReportSummaryPdfService;
        this.bikeReportSummaryService = bikeReportSummaryService;
    }

    @GetMapping("/report-summary")
    public String showSummaryPage(@RequestParam Long id,
                                  @RequestParam(required = false) Long compareTo,
                                  Model model) {

        BikeReportSummary summary = bikeReportSummaryService.getSummaryById(id);
        Long bikeInstanceId = summary.getBikeInstance().getId();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = null;

        if (auth != null && auth.getPrincipal() instanceof UserDetails userDetails) {
            userEmail = userDetails.getUsername();
        }

        if (summary != null && userEmail != null) {
            byte[] pdf = bikeReportSummaryPdfService.generatePdf(summary);
            try {
                emailService.sendSummaryEmailWithAttachment(
                        userEmail,
                        "Bike Report Summary",
                        "Your bike report summary is here and is attached!",
                        pdf,
                        "BikeReport_" + id + ".pdf"
                );
            } catch (Exception e) {
                model.addAttribute("emailError", e.getMessage());
            }
        }

        model.addAttribute("summaryId", id);
        model.addAttribute("bikeId", bikeInstanceId);
        return "bike_report_summary";
    }

    @GetMapping("/report-visualization")
    public String showGraphPage(@RequestParam(value = "id", required = false) Long summaryId, Model model) {
        if (summaryId != null) model.addAttribute("summaryId", summaryId);
        return "report-visualization";
    }

}
