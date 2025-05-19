package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.security.CustomUserDetails;
import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.service.intf.BikeInstanceService;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import be.kdg.swiftby.service.intf.UserService;
import be.kdg.swiftby.service.intf.BikeReportSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bikeowner")
public class BikeOwnerController {
    private final BikeOwnerService bikeOwnerService;
    private final BikeInstanceService bikeInstanceService;
    private final UserService userService;
    private final BikeReportSummaryService bikeReportSummaryService;

    @GetMapping("/bikes")
    public String showAllBikeOwnerBikes(Principal principal, Model model) {
        String email = principal.getName();

        Long bikeOwnerId = bikeOwnerService.getByEmail(email).getId();

        List<BikeInstance> bikeInstances = bikeInstanceService.getByBikeOwnerId(bikeOwnerId);

        model.addAttribute("bikeOwnerId", bikeOwnerId);
        model.addAttribute("bikeInstances", bikeInstances);

        return "all-bikes";
    }

    @GetMapping("bikeModels")
    public String showAllBikeInstances(Model model, @AuthenticationPrincipal CustomUserDetails userDetails){
        List<BikeInstance> bikes;
        switch (userDetails.getRole()){
            case "TECHNICIAN" -> bikes = bikeInstanceService.getAllByFacilityId(userDetails.getFacilityId());
            case "BIKEOWNER" -> bikes = bikeInstanceService.getByBikeOwnerEmail(userDetails.getUsername());
            default -> bikes = bikeInstanceService.getAll();
        }
        model.addAttribute("bikeInstances", bikes);
        return "bike-models";
    }

    @GetMapping("/bike-reports")
    public String showAllBikeReportSummaries(Principal principal, Model model) {
        String email = principal.getName();

        Long bikeOwnerId = bikeOwnerService.getByEmail(email).getId();

        List<BikeReportSummary> bikeReportSummaries =
                bikeReportSummaryService.getAllSummariesByBikeOwnerId(bikeOwnerId);

        model.addAttribute("bikeReportSummaries", bikeReportSummaries);

        return "bike-report-summaries-list";
    }



}
