package project.community.theatre.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import project.community.theatre.dto.requestDto.PaymentRequest;
import project.community.theatre.model.TicketEntity;
import project.community.theatre.repository.EventRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
@UtilityClass
public class PDFUtil {
    private EventRepository eventRepository;
    public static byte[] createPDF(byte[] qrCodeBytes, TicketEntity ticket) {
        log.info("Creating PDF for ticket: {}", ticket.getTicketNumber());
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Header
            Font headerFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLUE);
            Paragraph header = new Paragraph("Greenwich Community Theatre - Event Ticket", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            header.setSpacingAfter(20f);
            document.add(header);

            // Ticket Details
            Font detailFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);
            Paragraph ticketDetails = new Paragraph();
            ticketDetails.setAlignment(Element.ALIGN_LEFT);
            ticketDetails.add(new Chunk("Ticket ID: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getTicketNumber() + "\n", detailFont));
            ticketDetails.add(new Chunk("Name: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getUser().getName() + "\n", detailFont));
            ticketDetails.add(new Chunk("Event: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getEvent().getName(), detailFont));
            ticketDetails.add(new Chunk("\nDate: ", detailFont));
            ticketDetails.add(new Chunk(DateTimeConverter.getHumanReadableDate(ticket.getShowTime()), detailFont));
            ticketDetails.add(new Chunk("\nTime: ", detailFont));
            ticketDetails.add(new Chunk(DateTimeConverter.getHumanReadableTime(ticket.getShowTime()), detailFont));
            ticketDetails.add(new Chunk("\nSeat Numbers: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getSeatNumbers(), detailFont));
            ticketDetails.add(new Chunk("\nTotal Price: ", detailFont));
            ticketDetails.add(new Chunk(ticket.getTotalPrice() + "\n", detailFont));
            ticketDetails.add(new Chunk("\nVenue: ", detailFont));
            ticketDetails.add(new Chunk("GCT Main Stage", detailFont));
            ticketDetails.setSpacingAfter(20f);
            document.add(ticketDetails);

            // Centered QR Code
            Image qrImage = Image.getInstance(qrCodeBytes);
            qrImage.scaleToFit(150, 150);
            qrImage.setAlignment(Image.ALIGN_CENTER);
            qrImage.setSpacingBefore(10f);
            qrImage.setSpacingAfter(20f);
            document.add(qrImage);

            // Footer
            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC, BaseColor.GRAY);
            Paragraph footer = new Paragraph("Thank you for choosing GCT! Please present this ticket at the entrance. Enjoy the show!", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return baos.toByteArray();
        } catch (DocumentException e) {
            throw new RuntimeException("Failed to create PDF document: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("I/O error while creating PDF: " + e.getMessage(), e);
        }
    }
}