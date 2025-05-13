package be.kdg.swiftby.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BikeReportSummaryController {

    @GetMapping("/report-summary")
    public String showSummaryPage(@RequestParam Long id, Model model) {
        model.addAttribute("summaryId", id);
        return "bike_report_summary";
    }
}
