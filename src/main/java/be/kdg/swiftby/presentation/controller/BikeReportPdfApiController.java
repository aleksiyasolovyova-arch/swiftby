//package be.kdg.swiftby.presentation.controller;
//
//import be.kdg.swiftby.service.intf.BikeReportPdfService;
//import org.springframework.format.annotation.DateTimeFormat;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//
//@RestController
//@RequestMapping("/api/reports")
//public class BikeReportPdfApiController {
//
//    private final BikeReportPdfService bikeReportPdfService;
//
//    public BikeReportPdfApiController(BikeReportPdfService bikeReportPdfService) {
//        this.bikeReportPdfService = bikeReportPdfService;
//    }
//
//    @GetMapping("/bike/{bikeId}/pdf")
//    public ResponseEntity<byte[]> downloadBikeReportPdf(
//            @PathVariable Long bikeId,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reportDate) {
//
//        byte[] pdfBytes = bikeReportPdfService.generateBikeReportPdf(bikeId, reportDate);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_PDF);
//        headers.setContentDispositionFormData("attachment", "bike_report_" + bikeId + "_" + reportDate + ".pdf");
//
//        return ResponseEntity.ok().headers(headers).body(pdfBytes);
//    }
//}
