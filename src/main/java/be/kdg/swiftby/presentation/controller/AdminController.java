package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.testEnv.Administrator;
import be.kdg.swiftby.service.intf.AdministratorService;
import be.kdg.swiftby.service.intf.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@RequiredArgsConstructor
@Controller
public class AdminController {
    private final AdministratorService administratorService;

    @GetMapping("/admin/technicians")
    public String showTechnicianManagementPage(Authentication authentication, Model model) {
        String email = authentication.getName();
        Administrator admin = administratorService.getByEmail(email);
        model.addAttribute("facility", admin.getFacility());
        return "technician-management";
    }
}
