package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.service.intf.AdministratorService;
import be.kdg.swiftby.service.intf.TechnicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sysadmin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final AdministratorService administratorService;
    private final TechnicianService technicianService;

    @GetMapping("/unapproved-employees")
    public String showUnapprovedEmployees(Model model) {
        model.addAttribute("unapprovedAdmins", administratorService.getAllUnapproved());
        model.addAttribute("unapprovedTechnicians", technicianService.getAllUnapproved());
        return "sysadmin/unapproved-employees";
    }


    @PostMapping("/approve-admin/{id}")
    public String approveAdministrator(@PathVariable Long id) {
        administratorService.approve(id);
        return "redirect:/sysadmin/unapproved-employees";
    }

    @PostMapping("/approve-technician/{id}")
    public String approveTechnician(@PathVariable Long id) {
        technicianService.approve(id);
        return "redirect:/sysadmin/unapproved-employees";
    }

}
