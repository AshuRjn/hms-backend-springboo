package com.hms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String email;
    private Integer noOfGuest;

    @Column(name = "check-in-date", nullable = false)
    @FutureOrPresent(message = "Check-in date must be today or in the future")
    private LocalDate checkInDate;

    @Column(name = "check-out-date", nullable = false)
    @Future(message = "Check-out date must be in the future")
    private LocalDate checkOutDate;

    @Column(name = "created-date", nullable = false, updatable = false)
    private LocalDateTime createdDate = LocalDateTime.now(); // Auto-set

    @Column(name = "guest-phone", nullable = false, length = 15)
    @NotBlank(message = "Guest phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String guestPhone;

    @Column(name = "total-price", nullable = false)
    private Double totalPrice;

    @Column(name = "total_stay_days", nullable = false)
    private int totalStayDays;


    // Method to set createdDate before persisting the entity
    @PrePersist
    public void onCreate() {
        createdDate = LocalDateTime.now();
        // Set to current timestamp
    }

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Rooms room;

    public void setGuestPhone(String guestPhone) {
        if (guestPhone != null && !guestPhone.startsWith("+")) {
            this.guestPhone = "+91" + guestPhone.trim(); // Trim spaces & add +91 if missing
        } else {
            this.guestPhone = guestPhone;
        }
    }



}