package be.kdg.swiftby.presentation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/csv")
public class CsvController {



    @GetMapping("/upload")
    public String csv() {
        return "csv-upload";
    }
    @GetMapping("/read")
    public String csvRead() {
        return "csv-read";
    }
    @GetMapping("/results")
    public String csvResults() {
        return "csv-results-temporary";
    }

}
