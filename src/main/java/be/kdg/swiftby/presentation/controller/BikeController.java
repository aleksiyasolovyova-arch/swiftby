package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.domain.testEnv.Facility;
import be.kdg.swiftby.service.intf.AdministratorService;
import be.kdg.swiftby.service.intf.BikeInstanceService;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import be.kdg.swiftby.service.intf.TechnicianService;
import be.kdg.swiftby.domain.testEnv.User;
import be.kdg.swiftby.service.impl.UserUtilities;
import be.kdg.swiftby.service.intf.*;
import com.itextpdf.forms.xfdf.Mode;
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
    private final BikeInstanceService bikeInstanceService;
    private final BikeReportSummaryService bikeReportSummaryService;
    private final UserService userService;
    private final BikeOwnerService bikeOwnerService;
    private final BikeModelService bikeModelService;

    // TODO: USE EMPLOYEE SERVICE FOR BETTER ABSTRACTION
    @GetMapping("/bikes")
    public String showAllBikes(Principal principal, Model model) {
        String email = principal.getName();

        User user = userService.getUserByEmail(email);

        if (administratorService.existsByEmail(email) || technicianService.existsByEmail(email)) {
            Employee employee = (Employee) user;
            Facility facility = employee.getFacility();
            model.addAttribute("facilityId", facility.getId());
        } else if (bikeOwnerService.existsByEmail(email)) {
            BikeOwner bikeOwner = (BikeOwner) user;
            model.addAttribute("bikeOwnerId", bikeOwner.getId());
        }

        return "all-bikes";
    }
    @GetMapping("/bike-details")
    public String showBikeDetails(@RequestParam Long id, Model model) {
        BikeInstance bikeInstance = bikeInstanceService.getById(id);
        List<BikeReportSummary> summaries = bikeReportSummaryService.getSummariesByBikeId(id);

        model.addAttribute("bike", bikeInstance);
        model.addAttribute("summaries", summaries);
        return "bike_details";
    }

    @GetMapping("/bikeModels")
    public String showBikeModels(Model model){
        List<BikeModel> models = bikeModelService.getAll();
        model.addAttribute("models", models);
        return "bike-models";
    }
