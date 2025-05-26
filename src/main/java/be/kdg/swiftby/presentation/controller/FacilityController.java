package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class FacilityController {

    @GetMapping("/facility/{facilityId}/overview")
    public String facilityOverview(@PathVariable Long facilityId,
                                   @AuthenticationPrincipal CustomUserDetails userDetails,
                                   Model model) {
        if (!isAuthorized(userDetails, facilityId)) {
            return "forbidden";
        }
        model.addAttribute("facilityId", facilityId);
        return "administrator/facility-overview";
    }

    @GetMapping("/facility/{facilityId}/admin-bikeowners")
    public String bikeOwnersForAdmin(@PathVariable Long facilityId,
                                     @AuthenticationPrincipal CustomUserDetails userDetails,
                                     Model model) {
        if (!isAuthorized(userDetails, facilityId)) {
            return "forbidden";
        }
        model.addAttribute("facilityId", facilityId);
        return "administrator/admin-bikeowners";
    }

    @GetMapping("/facility/{facilityId}/admin-technicians")
    public String techniciansForAdmin(@PathVariable Long facilityId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails,
                                      Model model) {
        if (!isAuthorized(userDetails, facilityId)) {
            return "forbidden";
        }
        model.addAttribute("facilityId", facilityId);
        return "administrator/admin-technicians";
    }

    @GetMapping("/facility/{facilityId}/admin-testbenches")
    public String testBenchesForAdmin(@PathVariable Long facilityId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails,
                                      Model model) {
        if (!isAuthorized(userDetails, facilityId)) {
            return "forbidden";
        }
        model.addAttribute("facilityId", facilityId);
        return "administrator/admin-testbenches";
    }

    @GetMapping("/bikes")
    public String bikesForAdmin(@PathVariable Long facilityId,
                                @AuthenticationPrincipal CustomUserDetails userDetails,
                                Model model) {
        if (!isAuthorized(userDetails, facilityId)) {
            return "forbidden";
        }
        model.addAttribute("facilityId", facilityId);
        return "all-bikes";
    }

    private boolean isAuthorized(CustomUserDetails userDetails, Long facilityId) {
        return "SUPERADMIN".equalsIgnoreCase(userDetails.getRole()) ||
                (userDetails.getFacilityId() != null && facilityId.equals(userDetails.getFacilityId()));
    }
}
