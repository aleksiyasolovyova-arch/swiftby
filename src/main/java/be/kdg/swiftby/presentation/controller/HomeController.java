package be.kdg.swiftby.presentation.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class HomeController {
    public HomeController() {
    }

    @GetMapping
    public String home() {
        return "index";
    }


    @GetMapping("/workInProgress")
    public String wip() {
        return "work-in-progress";
    }


    @GetMapping("/customers")
    public String customers() {
        return "customers";
    }


}
