package be.kdg.swiftby.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/forbidden")
    public String forbidden() {
        return "forbidden";
    }

    @GetMapping("/error")
    public String error() {return "error";}
}
