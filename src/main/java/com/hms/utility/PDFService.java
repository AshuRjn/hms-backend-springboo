package com.hms.utility;

import com.hms.entity.Booking;
import com.hms.entity.Property;
import com.hms.repository.BookingRepository;
import com.hms.repository.PropertyRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;


@Service
public class PDFService {

    private PropertyRepository propertyRepository;
    private BookingRepository bookingRepository;

    public PDFService(PropertyRepository propertyRepository, BookingRepository bookingRepository) {
        this.propertyRepository = propertyRepository;
        this.bookingRepository = bookingRepository;
    }

    public Void generateBookingPdf(String filePath, Long propertyId, Booking booking) {

        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property with this id not found !" + propertyId));

        try {
            // Initialize Document
            Document document = new Document();

            // Create PdfWriter instance and link to the document
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Get the PdfContentByte to add borders and content
            PdfContentByte canvas = writer.getDirectContent();

            // Get the page size for positioning and drawing
            Rectangle pageSize = document.getPageSize();

            // Add border around the entire page
            canvas.setLineWidth(2f);  // Set border thickness
            canvas.rectangle(pageSize.getLeft(), pageSize.getBottom(), pageSize.getWidth(), pageSize.getHeight());
            canvas.stroke();  // Draw the border


            // Adding Hotel Name
            Font hotelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 36, new BaseColor(0, 128, 0)); // Emerald green
            Chunk hotelNameChunk = new Chunk("The Emerald Resort", hotelFont);
            hotelNameChunk.setUnderline(1f, -2f); // Underline thickness and position
            Paragraph hotelName = new Paragraph(hotelNameChunk);
            hotelName.setAlignment(Element.ALIGN_CENTER);
            hotelName.setSpacingAfter(10f);
            document.add(hotelName);

            // Hotel Address and Contact Info
            Paragraph hotelAddress = new Paragraph("123 Emerald Avenue, Paradise Bay\n" +
                    "Phone: +1-800-123-4567 | Email: info@theemeraldresort.com",
                    FontFactory.getFont(FontFactory.HELVETICA, 12, new BaseColor(128, 128, 128)));
            hotelAddress.setAlignment(Element.ALIGN_CENTER);
            hotelAddress.setSpacingAfter(20f);
            document.add(hotelAddress);

            // Adding a horizontal line to separate sections
            LineSeparator line = new LineSeparator();
            line.setPercentage(100);
            document.add(new Chunk(line));

            // Adding booking details heading
            Paragraph bookingDetailsHeading = new Paragraph("Booking Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            bookingDetailsHeading.setSpacingAfter(10f);
            document.add(bookingDetailsHeading);

            // Booking Information Table
            PdfPTable bookingTable = new PdfPTable(2);
            bookingTable.setWidthPercentage(100);
            bookingTable.setSpacingBefore(10f);

            // Table header
            PdfPCell cell = new PdfPCell(new Phrase("Booking Info"));
            cell.setColspan(2);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            bookingTable.addCell(cell);

            // Booking Info Cells
            bookingTable.addCell("Booking Id");
            bookingTable.addCell(booking.getId().toString());
            bookingTable.addCell("Booking Name");
            bookingTable.addCell(booking.getName());
            bookingTable.addCell("Email");
            bookingTable.addCell(booking.getEmail());
            bookingTable.addCell("Phone No");
            bookingTable.addCell(booking.getGuestPhone());
            bookingTable.addCell("Number of Guests");
            bookingTable.addCell(booking.getNoOfGuest().toString());
            bookingTable.addCell("Room Type");
            bookingTable.addCell(booking.getRoom().getRoomsType()); // Assuming the Room object is set
            bookingTable.addCell("Check-In Date");
            bookingTable.addCell(booking.getCheckInDate().toString());
            bookingTable.addCell("Check-Out Date");
            bookingTable.addCell(booking.getCheckOutDate().toString());
            document.add(bookingTable);

            // Adding property details heading
            Paragraph propertyDetailsHeading = new Paragraph("Accommodation Details ", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            propertyDetailsHeading.setSpacingBefore(20f);
            propertyDetailsHeading.setSpacingAfter(10f);
            document.add(propertyDetailsHeading);

            // Property Information Table
            PdfPTable propertyTable = new PdfPTable(2);
            propertyTable.setWidthPercentage(100);
            propertyTable.addCell("Accommodation ID");
            propertyTable.addCell(property.getId().toString());
            propertyTable.addCell("Venue Name ");
            propertyTable.addCell(property.getName());
            propertyTable.addCell("No of Rooms");
            propertyTable.addCell(property.getNoOfRooms().toString());
            propertyTable.addCell("No of Beds");
            propertyTable.addCell(property.getNoOfBeds().toString());
            propertyTable.addCell("No of Bathroom");
            propertyTable.addCell(property.getNoOfBathroom().toString());
            propertyTable.addCell("Country");
            propertyTable.addCell(property.getCountry().getCountryName());
            propertyTable.addCell("City");
            propertyTable.addCell(property.getCity().getCityName());
            propertyTable.addCell("ZipCode");
            propertyTable.addCell(property.getZipCode().getZipCode());
            document.add(propertyTable);

            // Signature Section
            Paragraph signature = new Paragraph("Signature:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            signature.setSpacingBefore(30f);
            signature.setSpacingAfter(10f);
            document.add(signature);

            // Add a line for signature (or image)
            Paragraph signatureLine = new Paragraph("______________________________");
            signatureLine.setAlignment(Element.ALIGN_LEFT);
            document.add(signatureLine);

            // Adding contact info at the bottom of the page
            Paragraph contactInfo = new Paragraph("For any inquiries or changes to your booking, please contact us at:\n" +
                    "Phone: +1-800-123-4567 | Email: support@theemeraldresort.com\n" +
                    "Visit our website: www.emeraldresort.com", FontFactory.getFont(FontFactory.HELVETICA, 12, new BaseColor(128, 128, 128)));
            contactInfo.setAlignment(Element.ALIGN_CENTER);
            contactInfo.setSpacingBefore(20f);
            document.add(contactInfo);

            // Closing the document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

