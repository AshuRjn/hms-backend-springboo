package com.hms.controller;

import com.hms.Service.CountryService;
import com.hms.payload.CountryDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/country")
public class CountryController {

    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/addCountry")
    public ResponseEntity<CountryDTO> createCountry(
            @RequestBody CountryDTO countryDto
    ){
        CountryDTO addedCountry = countryService.addCountry(countryDto);
        return new ResponseEntity<>(addedCountry, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return ResponseEntity.ok("Country deleted successfully.");
    }

    @GetMapping("/country")
    public ResponseEntity<List<CountryDTO>> getAllCountry(){
        List<CountryDTO> allCountry = countryService.getAllCountry();
        return ResponseEntity.ok(allCountry);
    }



}
