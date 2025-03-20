//package be.kdg.swiftby.service.impl;
//
//import be.kdg.swiftby.repository.report.BikeReportRepository;
//import be.kdg.swiftby.service.intf.BikeReportPdfService;
//import com.itextpdf.kernel.colors.DeviceRgb;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//import com.itextpdf.layout.element.Table;
//import com.itextpdf.layout.element.Cell;
//import com.itextpdf.layout.property.UnitValue;
//import com.itextpdf.layout.property.TextAlignment;
//import jakarta.transaction.Transactional;
//import org.springframework.stereotype.Service;
//
//import java.io.ByteArrayOutputStream;
//import java.time.LocalDate;
//import java.util.Optional;
//
//@Service
//@Transactional
//public class BikeReportPdfServiceImpl implements BikeReportPdfService {
//
//    private final BikeReportRepository bikeReportRepository;
//
//    public BikeReportPdfServiceImpl(BikeReportRepository bikeReportRepository) {
//        this.bikeReportRepository = bikeReportRepository;
//    }
//
//    @Override
//    public byte[] generateBikeReportPdf(Long bikeId, LocalDate reportDate) {
//        Optional<?> reportData = bikeReportRepository.getAggregatedBikeReport(bikeId, reportDate);
//
//        if (reportData.isEmpty()) {
//            throw new RuntimeException("⚠️ No reports found for bike " + bikeId + " on " + reportDate);
//        }
//
//        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
//            PdfWriter writer = new PdfWriter(outputStream);
//            PdfDocument pdf = new PdfDocument(writer);
//            Document document = new Document(pdf);
//
//            // 🎨 Define Colors
//            DeviceRgb darkBlue = new DeviceRgb(52, 74, 87);
//            DeviceRgb mediumBlue = new DeviceRgb(92, 114, 133);
//            DeviceRgb lightBlue = new DeviceRgb(174, 210, 227);
//            DeviceRgb softWhite = new DeviceRgb(245, 247, 255);
//            DeviceRgb warmYellow = new DeviceRgb(250, 218, 122);
//
//            // Extract Data (Handling NULL Values)
////            data = reportData.get();
////            double totalMileage = data[2] != null ? (double) data[2] : 0;
////            double avgAssistanceLevel = data[3] != null ? (double) data[3] : 0;
////            long reportCount = data[4] != null ? (long) data[4] : 0;
////            double totalBatteryCurrent = data[5] != null ? (double) data[5] : 0;
////            double totalBatteryVoltage = data[6] != null ? (double) data[6] : 0;
////            double totalBatteryCapacity = data[7] != null ? (double) data[7] : 0;
////            double avgBatteryTemperature = data[8] != null ? (double) data[8] : 0;
////            double totalMotorPower = data[9] != null ? (double) data[9] : 0;
////            double totalWheelSpeed = data[10] != null ? (double) data[10] : 0;
//
//            // 🏆 Title
//            document.add(new Paragraph("🚴 Bike Test Report")
//                    .setBold()
//                    .setFontSize(24)
//                    .setTextAlignment(TextAlignment.CENTER)
//                    .setFontColor(darkBlue)
//                    .setMarginBottom(20));
//
//            document.add(new Paragraph("📅 Report Date: " + reportDate)
//                    .setTextAlignment(TextAlignment.CENTER)
//                    .setMarginBottom(10));
//
//            document.add(new Paragraph("🔍 Total Reports: " + reportCount)
//                    .setBold()
//                    .setFontSize(14)
//                    .setMarginBottom(10));
//
//            // 🏆 General Info Table
//            Table generalTable = new Table(UnitValue.createPercentArray(new float[]{3, 3}))
//                    .useAllAvailableWidth()
//                    .setMarginBottom(10);
//            generalTable.addCell(createStyledCell("Total Mileage", mediumBlue, softWhite));
//            generalTable.addCell(createStyledCell(String.format("%.2f km", totalMileage), lightBlue, darkBlue));
//            generalTable.addCell(createStyledCell("Average Assistance Level", mediumBlue, softWhite));
//            generalTable.addCell(createStyledCell(String.format("%.2f", avgAssistanceLevel), lightBlue, darkBlue));
//            document.add(generalTable);
//
//            document.close();
//            return outputStream.toByteArray();
//        } catch (Exception e) {
//            throw new RuntimeException("❌ Error generating PDF", e);
//        }
//    }
//
//    private Cell createStyledCell(String content, DeviceRgb backgroundColor, DeviceRgb textColor) {
//        return new Cell()
//                .add(new Paragraph(content))
//                .setBackgroundColor(backgroundColor)
//                .setFontColor(textColor)
//                .setTextAlignment(TextAlignment.CENTER)
//                .setPadding(8);
//    }
//}
