package com.hms.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "rooms")
public class Rooms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "rooms_type", nullable = false)
    private String roomsType;

    @Column(name = "count", nullable = false)
    private Integer count;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "per_night_price", nullable = false)
    private Integer perNightPrice;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;



}