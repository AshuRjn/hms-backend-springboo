package com.hms.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookingDTO {
    private Integer id;
    private String name;
    private String email;
    private String guestPhone;
    private Integer noOfGuest;
    private String roomsType;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
