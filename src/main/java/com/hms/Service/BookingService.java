package com.hms.Service;

import com.hms.entity.Booking;
import com.hms.entity.Property;
import com.hms.entity.Rooms;
import com.hms.payload.BookingDTO;
import com.hms.repository.BookingRepository;
import com.hms.repository.PropertyRepository;
import com.hms.repository.RoomsRepository;
import com.hms.utility.EmailService;
import com.hms.utility.PDFService;
import com.hms.utility.PdfUploaderS3;
import com.hms.utility.TwilioService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingService {


    private BookingRepository bookingRepository;
    private TwilioService twilioService;
    private PropertyRepository propertyRepository;
    private RoomsRepository roomsRepository;
    private PDFService pdfService;
    private PdfUploaderS3 pdfUploaderS3;
    private EmailService emailService;
    private ModelMapper modelMapper;

    public BookingService(BookingRepository bookingRepository,
                          TwilioService twilioService,
                          PropertyRepository propertyRepository,
                          RoomsRepository roomsRepository,
                          PDFService pdfService,
                          PdfUploaderS3 pdfUploaderS3,
                          EmailService emailService,
                          ModelMapper modelMapper) {
        this.bookingRepository = bookingRepository;
        this.twilioService = twilioService;
        this.propertyRepository = propertyRepository;
        this.roomsRepository = roomsRepository;
        this.pdfService = pdfService;
        this.pdfUploaderS3 = pdfUploaderS3;
        this.emailService = emailService;
        this.modelMapper = modelMapper;
    }
    Booking mapToEntity(BookingDTO bookingDTO){
        return modelMapper.map(bookingDTO , Booking.class);
    }
    
    BookingDTO mapToDto(Booking booking){
        return modelMapper.map(booking, BookingDTO.class);
    }

 // Hotel Room Booking Logic here
    public String saveBooking(Long propertyId, BookingDTO bookingDTO) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new EntityNotFoundException("Property With this id not exists" + propertyId));

        Booking booking = mapToEntity(bookingDTO);
        String roomsType = bookingDTO.getRoomsType();

        // Fetch available rooms based on the provided booking criteria
        List<Rooms> rooms = roomsRepository.findRoomsBetweenDates(
                booking.getCheckInDate(), booking.getCheckOutDate(), roomsType, propertyId
        );

// Check if there are any available rooms in the fetched list
        Rooms availableRoom = null;
        for (Rooms room : rooms) {
            if (room.getCount() > 0) {
                availableRoom = room;  // Found an available room
                break;  // Exit loop
            }
        }

// If no room is available, return an appropriate message
        if (availableRoom == null) {
            return "No rooms available for dates: " + booking.getCheckInDate() + " to " + booking.getCheckOutDate();
        }
// ✅ Calculate the total stay days correctly
        long totalStayDays = ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());
        totalStayDays = Math.max(totalStayDays, 1); // Ensure at least 1 night

// ✅ Set total stay days in booking entity
        booking.setTotalStayDays((int) totalStayDays);

// ✅ Calculate the total price correctly based on the number of nights
        double totalPrice = availableRoom.getPerNightPrice() * totalStayDays;
        booking.setTotalPrice(totalPrice);

// ✅ Associate the available room with the booking
        booking.setRoom(availableRoom);

// ✅ Save the booking and return the response
        Booking savedBooking = bookingRepository.save(booking);
        BookingDTO booked = mapToDto(savedBooking);

// ✅ Reduce room count since it's being booked
        availableRoom.setCount(availableRoom.getCount() - 1);
        roomsRepository.save(availableRoom);  // Update room count in DB



        // ✅ Generate PDF
            String pdfPath = "C:\\hms_booking\\HotelBooking_" + savedBooking.getId() + ".pdf";
            pdfService.generateBookingPdf(pdfPath, propertyId ,booking);

        // ✅ Upload PDF to S3
        String s3Url = pdfUploaderS3.uploadPdfToS3(pdfPath, "HotelBooking_" + booking.getId() + ".pdf");

        // ✅ Send SMS Confirmation
        String smsMessage = "Booking successful!\n" +
                "Dear " + booking.getName() + ",\n" +
                "Your stay at The Emerald Resort is confirmed!\n" +
                "Check-in: " + booking.getCheckInDate() + "\n" +
                "Check-out: " + booking.getCheckOutDate() + "\n" +
                "For assistance, Phone: +1-800-123-4567.\n" +
                "We look forward to welcoming you!";

        twilioService.sendSms(booking.getGuestPhone(), smsMessage);

        //  ✅ Send WhatsApp message
        twilioService.sendWhatsApp(booking.getGuestPhone(), smsMessage);

        // ✅ Send Email with PDF
//        try {
//            emailService.sendBookingConfirmation(booking.getEmail(), pdfPath, booking);
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }

    // ✅ Return Response Message
        return "Booking confirmed! 🎉\n" +
                "Your stay at *The Emerald Resort* is successfully booked.\n" +
                "Check-in: " + booking.getCheckInDate() + "\n" +
                "Check-out: " + booking.getCheckOutDate() + "\n" +
                "A confirmation PDF has been generated and sent to your email.\n" +
                "For any assistance, contact us at: +1-800-123-4567.";

        }
    }

