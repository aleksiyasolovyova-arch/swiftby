package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.service.intf.AdministratorService;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import be.kdg.swiftby.service.intf.BikeService;
import be.kdg.swiftby.service.intf.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class BikeController {
    private final AdministratorService administratorService;
    private final TechnicianService technicianService;
    private final BikeService bikeService;
    private final BikeReportSummaryService bikeReportSummaryService;
    // TODO: USE EMPLOYEE SERVICE FOR BETTER ABSTRACTION
    @GetMapping("/bikes")
    public String showAllBikesForFacility(Principal principal, Model model) {
        String email = principal.getName();

        Facility facility = null;
        try {
            facility = administratorService.getByEmail(email).getFacility();
        } catch (Exception ignored) {}

        if (facility == null) {
            try {
                facility = technicianService.getByEmail(email).getFacility();
            } catch (Exception ignored) {}
        }

        model.addAttribute("facilityId", facility.getId());
        return "all-bikes";
    }
    @GetMapping("/bike-details")
    public String showBikeDetails(@RequestParam Long id, Model model) {
        Bike bike = bikeService.getById(id);
        List<BikeReportSummary> summaries = bikeReportSummaryService.getSummariesByBikeId(id);

        model.addAttribute("bike", bike);
        model.addAttribute("summaries", summaries);
        return "bike_details";
    }

}
