package be.kdg.swiftby.presentation.controller;

import be.kdg.swiftby.csv.CsvService;
import be.kdg.swiftby.csv.FileStorageService;
import be.kdg.swiftby.domain.report.BikeReport;
import be.kdg.swiftby.service.intf.BikeReportService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/csv")
@AllArgsConstructor
public class CsvApiController {
    private final FileStorageService fileStorageService;
    private final BikeReportService bikeReportService;
    private final CsvService csvService;

    private static final String UPLOAD_DIR = "src/main/resources/uploads/";


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

    @GetMapping("/results")
    public List<BikeReport> getBikeResults() {
        return bikeReportService.getAll();
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> getCSVFile(@PathVariable String fileName) {
        File file = new File(UPLOAD_DIR + fileName);

        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + file.getName())
                .body(resource);
    }


}
