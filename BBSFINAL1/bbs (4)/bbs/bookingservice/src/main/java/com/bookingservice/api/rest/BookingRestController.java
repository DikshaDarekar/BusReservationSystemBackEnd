package com.bookingservice.api.rest;


import com.bookingservice.dto.BookingDetailsDTO;
import com.bookingservice.entities.BookingDetails;
import com.bookingservice.exceptions.CustomException;
import com.bookingservice.services.BookingService;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookingservice")
@Slf4j
public class BookingRestController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public ResponseEntity<?> save(@RequestBody BookingDetails bookingDetails) {
        log.info("Rest request to book bus");
        try {
            long bookingDetailsId=this.bookingService.save(bookingDetails);
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("bookingDetailsId",bookingDetailsId);
            responseMap.put("message","Bus booked successfully");
            return ResponseEntity.ok().body(responseMap);
        } catch (CustomException customException) {
            if (customException.getExceptions() != null && !customException.getExceptions().isEmpty())
                return ResponseEntity.badRequest().body(customException.getExceptions());
            else return ResponseEntity.badRequest().body(customException.getException());
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/book/findall")
    public ResponseEntity<?> findAll(Pageable pageable) {
        try {
            Page<BookingDetailsDTO> page = this.bookingService.findAll(pageable);
            return ResponseEntity.ok().body(page);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }


    @GetMapping("/book/findAllByUserId")
    public ResponseEntity<?> findByUserId(@RequestParam("userId") long userId) {
        try {
            List<BookingDetailsDTO> bookingDetailsList = this.bookingService.findAllByUserId(userId);
            return ResponseEntity.ok().body(bookingDetailsList);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @DeleteMapping("/book/cancel/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        try {
            this.bookingService.delete(id);
            Map<String,Object> responseMap=new HashMap<>();
            responseMap.put("message","Bus booking cancelled successfully");
            return ResponseEntity.ok().body(responseMap);
        } catch (Exception exception) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }

    @GetMapping("/download-ticket")
    public void generatePdf(@RequestParam("id") long id,HttpServletResponse response) {
        try {
            // Set the content type and headers for the response
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=ticket.pdf");

            BookingDetailsDTO bookingDetailsDTO=this.bookingService.findById(id);
            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

            // Create a new Document
            Document document = new Document();
            // Create PdfWriter for the response's OutputStream
            PdfWriter.getInstance(document, response.getOutputStream());
            PdfPTable table = new PdfPTable(2);

            // Open the Document
            document.open();
            Paragraph paragraph=new Paragraph(Element.ALIGN_CENTER, "BBS Ticket",boldFont);
            paragraph.setSpacingAfter(20f); //
            document.add(paragraph);

            // Add content to the Document
            table.addCell(new Paragraph("Ref. No.",boldFont));
            table.addCell(new Paragraph("BBS"+bookingDetailsDTO.getId()));

            table.addCell(new Paragraph("From",boldFont));
            table.addCell(new Paragraph(bookingDetailsDTO.getBusDetails().get("fromLocation").toString()));
            table.addCell(new Paragraph("To",boldFont));
            table.addCell(new Paragraph(bookingDetailsDTO.getBusDetails().get("toLocation").toString()));

            table.addCell(new Paragraph("Booked By",boldFont));
            table.addCell(new Paragraph(bookingDetailsDTO.getBookedBy().get("firstName").toString()+" "+bookingDetailsDTO.getBookedBy().get("lastName").toString()));

            table.addCell(new Paragraph("Booking Time",boldFont));
            table.addCell(new Paragraph(bookingDetailsDTO.getBookedAt().toString()));

            table.addCell(new Paragraph("Fare",boldFont));
            table.addCell(new Paragraph(bookingDetailsDTO.getBusDetails().get("fare").toString()));
            log.info(bookingDetailsDTO.getSeatsToBook()+"");
            table.addCell(new Paragraph("Total Seats Booked",boldFont));
            table.addCell(new Paragraph(bookingDetailsDTO.getSeatsToBook()+""));

            table.addCell(new Paragraph("Total Amount",boldFont));
            table.addCell(new Paragraph(bookingDetailsDTO.getTotalAmount().toString()));
            document.add(table);
            // Close the Document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
