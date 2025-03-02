package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.csv.FileStorageService;
import be.kdg.swiftby.service.intf.BikeReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class CsvApiController {
    private final FileStorageService fileStorageService;

    public CsvApiController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("csv") MultipartFile csv) {

        String message = fileStorageService.storeFile(csv);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/read/{fileName}")
    public ResponseEntity<List<String[]>> readFile(@PathVariable String fileName) {
        List<String[]> data = fileStorageService.readFile(fileName);
        return ResponseEntity.ok(data);
    }
}
