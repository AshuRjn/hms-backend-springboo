package com.hms.controller;

import com.hms.Service.CityService;
import com.hms.payload.CityDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/city")
public class CityController {

    private CityService cityService;

    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping("/addCity")
    public ResponseEntity<CityDTO> createCity(
            @RequestBody CityDTO cityDTO
    ){
        CityDTO addedCity = cityService.addCity(cityDTO);
        return new ResponseEntity<>(addedCity, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteCity(
        @PathVariable Long id
    ){
        cityService.deleteCity(id);
        return new ResponseEntity<>("Country deleted successfully !",HttpStatus.OK);
    }
}
