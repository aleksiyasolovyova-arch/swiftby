package be.kdg.swiftby.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FacilityController {
    @GetMapping("/facility/{id}/overview")
    public String facilityOverview(@PathVariable Long id, Model model) {
        model.addAttribute("facilityId", id);
        return "facility-overview";
    }
}
