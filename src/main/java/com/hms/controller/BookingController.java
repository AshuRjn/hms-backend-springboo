package com.hms.controller;

import com.hms.Service.BookingService;
import com.hms.payload.BookingDTO;
import com.hms.utility.PDFService;
import com.hms.repository.BookingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;

    }

// Booked hotel, and it will generate pdf of booking and send it to email or sms
    @PostMapping("/create-booking")
    public ResponseEntity<String> createBooking(
            @RequestParam Long propertyId,
            @RequestBody BookingDTO bookingDTO
    ) {
        String saveBooking = bookingService.saveBooking(propertyId, bookingDTO);

        return new ResponseEntity<>(saveBooking,HttpStatus.OK);
    }






}
