package be.kdg.swiftby.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class FunctionalityCheckController {
    @GetMapping("/functional-check")
    public String showFunctionalCheckForm(@RequestParam UUID testId, Model model) {
        model.addAttribute("testId", testId);
        return "functional_check";
    }
}
