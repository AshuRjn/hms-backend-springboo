package com.hms.controller;

import com.hms.Service.PropertyService;
import com.hms.entity.Property;
import com.hms.payload.PropertyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/property")
public class PropertyController {

    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    @PostMapping("/add")
    public ResponseEntity<PropertyDTO> createProperty(
            @RequestBody PropertyDTO propertyDTO
    ){
        PropertyDTO addProperty = propertyService.addProperty(propertyDTO);
        return new ResponseEntity<>(addProperty, HttpStatus.CREATED);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<String> deleteProperty(
            @PathVariable Long id
    ){
        propertyService.deleteProperty(id);
        return  ResponseEntity.ok("Property which id is "+id+"  deleted successfully !");
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PropertyDTO>> getAllProperty(){
        List<PropertyDTO> allProperty = propertyService.getAllProperty();
        return new ResponseEntity<>(allProperty,HttpStatus.OK);
    }

    @GetMapping("/Property/{id}")
    public ResponseEntity<?> getPropertyById(
            @PathVariable Long id
    ){
        PropertyDTO propertyById = propertyService.getPropertyById(id);
        if (propertyById != null){
            return new ResponseEntity<>(propertyById,HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Property with" +id+ "is not found",HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PropertyDTO> updateProperty(
            @PathVariable Long id,
            @RequestBody PropertyDTO propertyDTO
    ){
        PropertyDTO updateProperty = propertyService.updateProperty(id, propertyDTO);
        return new ResponseEntity<>(updateProperty,HttpStatus.OK);
    }


    // name use to pass any filed country state city etc
    @GetMapping("/search-hotels")
    public ResponseEntity<List<PropertyDTO>> searchHotels(
            @RequestParam String name
    ){
        List<PropertyDTO> propertyDTOList = propertyService.searchHotels(name);
        return ResponseEntity.ok(propertyDTOList);
    }


}
