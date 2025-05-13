package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.repository.testEnvironment.AdministratorRepository;
import be.kdg.swiftby.repository.testEnvironment.TechnicianRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class FacilitySessionAdvice {
    private final TechnicianRepository technicianRepo;
    private final AdministratorRepository adminRepo;

    public FacilitySessionAdvice(TechnicianRepository technicianRepo, AdministratorRepository adminRepo) {
        this.technicianRepo = technicianRepo;
        this.adminRepo = adminRepo;
    }

    @ModelAttribute
    public void addFacilityIdToSession(HttpSession session, Authentication authentication) {
        if (authentication == null) return;

        String email = authentication.getName();

        if (session.getAttribute("facilityId") == null) {
            technicianRepo.findByEmail(email)
                    .map(tech -> tech.getFacility().getId())
                    .ifPresentOrElse(
                            id -> session.setAttribute("facilityId", id),
                            () -> adminRepo.findAdministratorByEmail(email)
                                    .map(admin -> admin.getFacility().getId())
                                    .ifPresent(id -> session.setAttribute("facilityId", id))
                    );
        }
    }

}
