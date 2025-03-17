package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.csv.CsvService;
import be.kdg.swiftby.csv.FileStorageService;
import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.service.intf.BikeReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/csv")
@AllArgsConstructor
public class CsvApiController {
    private final FileStorageService fileStorageService;
    private final BikeReportService bikeReportService;
    private final CsvService csvService;

//
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("csv") MultipartFile csv) {
//        String message = fileStorageService.storeFile(csv);
//        return ResponseEntity.ok(message);
//    }
//
//    @GetMapping("/read/{fileName}")
//    public ResponseEntity<List<String[]>> readFile(@PathVariable String fileName) {
//        List<String[]> data = fileStorageService.readFile(fileName);
//        return ResponseEntity.ok(data);
//    }
//
//    @GetMapping("/results")
//    public List<BikeReport> getBikeResults(){
//        return bikeReportService.getAll();
//    }
//
//

}
