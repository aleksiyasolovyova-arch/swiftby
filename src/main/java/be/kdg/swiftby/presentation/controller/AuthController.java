package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.service.impl.PasswordResetService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {
    private final PasswordResetService resetService;

    public AuthController(PasswordResetService resetService) {
        this.resetService = resetService;
    }

    @GetMapping("/set-password")
    public String showSetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "set-password";
    }

    @PostMapping("/api/auth/set-password")
    public String setPassword(@RequestParam String token, @RequestParam String password) {
        resetService.setPassword(token, password);
        return "redirect:/login?passwordSet";
    }
}
