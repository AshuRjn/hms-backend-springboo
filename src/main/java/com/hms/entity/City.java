package com.hms.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "city")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Getter
    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true )
    @JsonBackReference // ❌ Prevents infinite loop, City won't include properties in response
    private List<Property> properties = new ArrayList<>();

}