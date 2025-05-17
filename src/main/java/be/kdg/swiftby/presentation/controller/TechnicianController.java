package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.service.intf.TechnicianService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/technician")
public class TechnicianController {
    TechnicianService technicianService;

    @GetMapping("dashboard")
    public String dashboard() {
        return "technician/dashboard";
    }

    @GetMapping("reports")
    public String reports() {
        return "technician/reports";
    }


}
