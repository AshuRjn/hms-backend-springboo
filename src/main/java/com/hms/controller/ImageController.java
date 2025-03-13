package com.hms.controller;

import com.hms.Service.ImageService;
import com.hms.Service.PropertyService;
import com.hms.Service.S3BucketService;
import com.hms.entity.AppUser;
import com.hms.entity.Images;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private S3BucketService bucketService;
    private final ImageService imageService;

    public ImageController(S3BucketService bucketService, ImageService imageService) {
        this.bucketService = bucketService;

        this.imageService = imageService;
    }

    @PostMapping(path = "/upload/file/{bucketName}/property/{propertyId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> uploadFile(
            @RequestParam MultipartFile file,
            @PathVariable String bucketName,
            @PathVariable long propertyId,
            @AuthenticationPrincipal AppUser user
    ) {

        // Upload the file to S3 and get the image URL
        String imageUrl = bucketService.uploadFile(file, bucketName);

        // Save the uploaded image URL in the database and associate it with the given property ID
        Images saveImage = imageService.uploadPropertyImages(imageUrl, propertyId);

        return new ResponseEntity<>(saveImage, HttpStatus.OK);
    }

    @GetMapping("/property/{propertyId}")
    public ResponseEntity<List<?>> getPropertyImages(
            @PathVariable Long propertyId
    ){
        List<String> propertyImages = imageService.getPropertyImages(propertyId);
        if (propertyImages.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonList("There are no images for this property!"));
        }
        return ResponseEntity.ok(propertyImages);
    }

//  delete all property image from db only
    @DeleteMapping("/property/{propertyId}")
    public ResponseEntity<?> deletePropertyImages(
            @PathVariable Long propertyId
    ) {
        imageService.deleteAllPropertyImages(propertyId);
        return ResponseEntity.ok("Images deleted successfully.");
    }

// delete all property image from db and s3 bucket
    @DeleteMapping("/property")
    public ResponseEntity<String> deletePropertyImage(
            @RequestParam Long propertyId
    ){
       imageService.deletePropertyImages(propertyId);
       return new ResponseEntity<>("Image successfully deleted !",HttpStatus.OK);
    }

}
