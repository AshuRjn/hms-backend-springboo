package com.hms.entity;

import jakarta.persistence.*;
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
    private LocalDate checkInDate;

    @Column(name = "check-out-date", nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "created-date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "guest-phone", nullable = false, length = 15)
    private String guestPhone;

    // Method to set createdDate before persisting the entity
    @PrePersist
    public void onCreate() {
        createdDate = LocalDateTime.now();  // Set to current timestamp
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