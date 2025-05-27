package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.domain.bike.Condition;
import be.kdg.swiftby.domain.report.VisualInspection;
import be.kdg.swiftby.service.impl.VisualInspectionServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class VisualInspectionController {

    private final VisualInspectionServiceImpl visualInspectionService;

    public VisualInspectionController(VisualInspectionServiceImpl visualInspectionService) {
        this.visualInspectionService = visualInspectionService;
    }

    @GetMapping("/visual-inspection")
    public String showVisualInspectionForm(
                                           @RequestParam Long summaryId,
                                           Model model
    ) {
        Field[] fields = VisualInspection.class.getDeclaredFields();

        List<String> components = Arrays.stream(fields)
                .filter(field -> !field.getName().equals("id"))
                .map(Field::getName)
                .toList();

        List<Condition> conditions = Arrays.asList(Condition.values());

        model.addAttribute("components", components);
        model.addAttribute("conditions", conditions);
        model.addAttribute("summaryId", summaryId);

        return "visual-inspection";
    }

    @PostMapping("/visual-inspection")
    public String handleFormSubmit(@RequestParam UUID testId,
                                   @RequestParam(required = false) Long summaryId,
                                   @RequestParam Map<String, String> allParams) {
        allParams.remove("testId");
        if (summaryId != null) allParams.remove("summaryId");
        VisualInspection visualInspection = new VisualInspection();

        allParams.forEach((key, value) -> {
            try {
                Field field = VisualInspection.class.getDeclaredField(key);
                field.setAccessible(true);
                field.set(visualInspection, Condition.valueOf(value));
            } catch (NoSuchFieldException | IllegalAccessException ignored) {}
        });

        if (summaryId != null) {
            visualInspectionService.saveAndLinkReport(summaryId, visualInspection);
        } else {
            visualInspectionService.saveInspection(visualInspection);
        }
        return "report-summary?id=" + summaryId;
    }

}
