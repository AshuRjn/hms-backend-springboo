package com.hms.controller;

import com.hms.Service.ZipCodeService;
import com.hms.payload.ZipCodeDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/zipcode")
public class ZipCodeController {

    private final ZipCodeService zipCodeService;

    public ZipCodeController(ZipCodeService zipCodeService) {
        this.zipCodeService = zipCodeService;
    }

    @PostMapping("/add")
    public ResponseEntity<ZipCodeDTO> createZipCode(
            @RequestBody ZipCodeDTO zipDTO
    ){
        ZipCodeDTO addedZip = zipCodeService.addZipCode(zipDTO);
        return new ResponseEntity<>(addedZip, HttpStatus.CREATED);
    }
}
