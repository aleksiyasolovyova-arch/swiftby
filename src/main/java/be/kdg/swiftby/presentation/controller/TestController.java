package be.kdg.swiftby.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class TestController {

    @GetMapping("checklist")
    public String showPreTestCheckList(){
        return "startTest/checklist";
    }
    @GetMapping("startTest")
    public String idkAnymore(){
        return "startTest/find-user";
    }

    @GetMapping("startTest/checklist")
    public String checklist(){
        return "startTest/checklist";
    }

    @GetMapping("startTest/find-user")
    public String showFindUserPage() {
        return "startTest/find-user";
    }
    @GetMapping("startTest/select-bike")
    public String showSelectBikePage(@RequestParam("userId") Long userId, Model model) {
        model.addAttribute("userId", userId);
        return "startTest/select-bike";
    }
    @GetMapping("startTest/test-setup")
    public String showTestSetupPage(@RequestParam("bikeId") Long bikeId, Model model) {
        model.addAttribute("bikeId", bikeId);
        return "startTest/test-setup";
    }
}
