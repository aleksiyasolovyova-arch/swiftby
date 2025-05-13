package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.bike.Bike;
import be.kdg.swiftby.service.intf.BikeOwnerService;
import be.kdg.swiftby.service.intf.BikeService;
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
    private final BikeService bikeService;


    @GetMapping("/bikes")
    public String showAllBikeOwnerBikes(Principal principal, Model model) {
        String email = principal.getName();

        Long bikeOwnerId = bikeOwnerService.getByEmail(email).getId();

        List<Bike> bikes = bikeService.getByBikeOwnerId(bikeOwnerId);

        model.addAttribute("bikeOwnerId", bikeOwnerId);

        return "all-bikes";

    }
}
