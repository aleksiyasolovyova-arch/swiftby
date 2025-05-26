package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.bike.Condition;
import be.kdg.swiftby.domain.report.VisualInspection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

@Controller
public class VisualInspectionController {

    @GetMapping("/visual-inspection")
    public String showVisualInspectionForm(Model model) {
        Field[] fields = VisualInspection.class.getDeclaredFields(); // <-- FIXED

        List<String> components = Arrays.stream(fields)
                .filter(field -> !field.getName().equals("id"))
                .map(Field::getName)
                .toList();

        List<Condition> conditions = Arrays.asList(Condition.values());

        model.addAttribute("components", components);
        model.addAttribute("conditions", conditions);
        return "visual-inspection";
    }

}
