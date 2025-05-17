package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class FacilityController {

    @GetMapping("/facility/{id}/overview")
    public String facilityOverview(@PathVariable Long id, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if ("SUPERADMIN".equalsIgnoreCase(userDetails.getRole())) {
            return "facility-overview";
        }
        if (userDetails.getFacilityId() == null || !id.equals(userDetails.getFacilityId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Not your facility.");
        }

        return "facility-overview";
    }
}
