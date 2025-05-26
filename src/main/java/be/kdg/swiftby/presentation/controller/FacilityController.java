package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class FacilityController {

    @GetMapping("/facility/{facilityId}/overview")
    public String facilityOverview(@PathVariable Long facilityId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        if ("SUPERADMIN".equalsIgnoreCase(userDetails.getRole())) {
            return "facility-overview";
        }
        if (userDetails.getFacilityId() == null || !facilityId.equals(userDetails.getFacilityId())) {
            return "forbidden";
        }

        return "facility-overview";
    }
}
