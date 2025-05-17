package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.bike.BikeInstance;
import be.kdg.swiftby.service.intf.BikeInstanceService;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/bikeowner")
public class BikeOwnerController {
    private final BikeOwnerService bikeOwnerService;
    private final BikeInstanceService bikeInstanceService;

    @GetMapping("/bikes")
    public String showAllBikeOwnerBikes(Principal principal, Model model) {
        String email = principal.getName();

        Long bikeOwnerId = bikeOwnerService.getByEmail(email).getId();

        List<BikeInstance> bikeInstances = bikeInstanceService.getByBikeOwnerId(bikeOwnerId);

        model.addAttribute("bikeOwnerId", bikeOwnerId);
        model.addAttribute("bikeInstances", bikeInstances);

        return "all-bikes";

    }
}
