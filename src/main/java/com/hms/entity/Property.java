package com.hms.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "no_of_guest", nullable = false)
    private Integer noOfGuest;

    @Column(name = "no_of_rooms", nullable = false)
    private Integer noOfRooms;

    @Column(name = "no_of_bathroom", nullable = false)
    private Integer noOfBathroom;

    @Column(name = "no_of_beds", nullable = false)
    private Integer noOfBeds;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne()
    @JoinColumn(name = "city_id")
    @JsonManagedReference // ✅ Allows Property to include City in API responses
    private City city;

    @ManyToOne
    @JoinColumn(name = "zip_code_id")
    private ZipCode zipCode;

    @ManyToOne
    @JoinColumn(name = "state_id" ,nullable = true)
    private State state;

}