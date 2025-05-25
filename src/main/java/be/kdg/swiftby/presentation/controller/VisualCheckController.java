package be.kdg.swiftby.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class VisualCheckController {
    @GetMapping("/visual_check")
    public String showVisualCheckForm(@RequestParam UUID testId, Model model){
        model.addAttribute("testId", testId);
        return "visual_check";
    }
}