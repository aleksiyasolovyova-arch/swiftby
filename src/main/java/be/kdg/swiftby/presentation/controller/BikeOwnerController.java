package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.service.intf.BikeReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class BikeOwnerController {
    private final BikeReportService bikeReportService;
    // the endpoint to the report page without any information that should be private
    // would be accessed through the qr code
    @GetMapping("/{reportId}")
    public String showReport(
    
    ){
        return "bikeOwnerResults";
    }
}
