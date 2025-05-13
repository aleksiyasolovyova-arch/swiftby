package be.kdg.swiftby.service.impl;

import be.kdg.swiftby.domain.report.BikeReportSummary;
import be.kdg.swiftby.service.intf.BikeReportSummaryPdfService;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class BikeReportSummaryPdfServiceImpl implements BikeReportSummaryPdfService {

    // Color Palette
    private static final BaseColor BACKGROUND_COLOR = new BaseColor(173, 210, 227); // #aed2e3 (Light blue background)
    private static final BaseColor HEADER_COLOR = new BaseColor(250, 218, 122); // #fada7a (Yellow for header)
    private static final BaseColor TEXT_COLOR = new BaseColor(52, 74, 87); // #344a57 (Dark blue for text)
    private static final BaseColor TABLE_BACKGROUND_COLOR = new BaseColor(220, 240, 255); // Light blue for table

    @Override
    public byte[] generatePdf(BikeReportSummary summary) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 36, 36, 36, 36);
            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();

            // title font
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, TEXT_COLOR);
            Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, TEXT_COLOR);
            Font valueFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, TEXT_COLOR);

            Paragraph title = new Paragraph("Bike Test Report Summary\n\n", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);


            // report details
            addStyledCard(document, "Report Details", labelFont, valueFont, "Report Date:", summary.getReportTime().format(DateTimeFormatter.ISO_DATE));

            //performance Data Section
            addStyledCard(document, "Performance Data", labelFont, valueFont,
                    "Average Mileage:", String.format("%.2f km", summary.getAvgMileage()),
                    "Average Assistance Level:", String.format("%.2f%%", summary.getAvgAssistanceLevel()),
                    "Speed:", String.format("%.2f km/h", summary.getSpeed()),
                    "Power:", String.format("%.2f W", summary.getPower()),
                    "Horizontal Inclination:", String.format("%.2f°", summary.getHorizontalInclination()),
                    "Vertical Inclination:", String.format("%.2f°", summary.getVerticalInclination()));

            //battery & engine Data Section
            addStyledCard(document, "Battery & Engine Data", labelFont, valueFont,
                    "Charge Status:", summary.isChargeStatus() ? "Charging" : "Not Charging",
                    "Battery Current:", String.format("%.2f A", summary.getBatteryCurrent()),
                    "Battery Voltage:", String.format("%.2f V", summary.getVoltage()),
                    "Battery Capacity:", String.format("%.2f Ah", summary.getCapacity()),
                    "Battery Temperature:", String.format("%.2f°C", summary.getTemperature()),
                    "Engine Type:", summary.getEngineType(),
                    "Gear Type:", summary.getGearType());

            //motor &load Data Section
            addStyledCard(document, "Motor & Load Data", labelFont, valueFont,
                    "Max Power:", String.format("%d W", summary.getMaxPower()),
                    "Nominal Power:", String.format("%d W", summary.getNominalPower()),
                    "Torque:", String.format("%d Nm", summary.getTorque()),
                    "Torque Crank:", String.format("%.2f Nm", summary.getTorqueCrank()),
                    "Cadence:", String.format("%d RPM", (int) summary.getCadence()),
                    "Roller Torque:", String.format("%.2f Nm", summary.getRollerTorque()),
                    "Load Cell:", String.format("%.2f N", summary.getLoadCell()),
                    "Load Power:", String.format("%d W", summary.getLoadPower()),
                    "Rol:", String.format("%.2f", summary.getRol()),
                    "Status Plug:", summary.isStatusPlug() ? "Plugged In" : "Not Plugged");


            //technician Notes Section
            addStyledCard(document, "Technician Notes", labelFont, valueFont, "Technician Comment:", summary.getTechnicianComment() != null ? summary.getTechnicianComment() : "No comments");


            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
    }

    private void addStyledCard(Document doc, String cardTitle, Font labelFont, Font valueFont, String... lines) throws DocumentException {
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);

        // card header
        PdfPCell headerCell = new PdfPCell(new Phrase(cardTitle, new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, TEXT_COLOR)));
        headerCell.setBackgroundColor(HEADER_COLOR);
        headerCell.setBorder(Rectangle.NO_BORDER);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(headerCell);

        //  content
        for (int i = 0; i < lines.length; i += 2) {
            PdfPCell labelCell = new PdfPCell(new Phrase(lines[i], labelFont));
            PdfPCell valueCell = new PdfPCell(new Phrase(lines[i + 1], valueFont));
            labelCell.setBackgroundColor(TABLE_BACKGROUND_COLOR);
            valueCell.setBackgroundColor(TABLE_BACKGROUND_COLOR);
            labelCell.setPadding(8);
            valueCell.setPadding(8);
            table.addCell(labelCell);
            table.addCell(valueCell);
        }

        doc.add(table);
    }
}
