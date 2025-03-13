package com.hms.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.hms.entity.Images;
import com.hms.entity.Property;
import com.hms.repository.ImagesRepository;
import com.hms.repository.PropertyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ImageService {


    private final PropertyRepository propertyRepository;
    private final ImagesRepository imagesRepository;
    private AmazonS3 amazonS3;

    public ImageService(PropertyRepository propertyRepository, ImagesRepository imagesRepository, AmazonS3 amazonS3) {
        this.propertyRepository = propertyRepository;
        this.imagesRepository = imagesRepository;
        this.amazonS3 = amazonS3;
    }



    public Images uploadPropertyImages(String imageUrl, long propertyId) {
        Property property = propertyRepository.findById(propertyId)
                .orElseThrow(() -> new RuntimeException("Property not found with given propertyId" + propertyId));

        Images image = new Images();
        image.setUrl(imageUrl);
        image.setProperty(property);

        return imagesRepository.save(image);
    }

    public List<String> getPropertyImages(Long propertyId) {


        List<Images> imagesList = imagesRepository.findByPropertyId(propertyId);

        // Return only image URLs
        return imagesList.stream()
                .map(Images::getUrl)
                .collect(Collectors.toList());
    }

    // Delete all property image from database
    public void deleteAllPropertyImages(Long propertyId) {
        List<Images> imagesList = imagesRepository.findByPropertyId(propertyId);

        if (imagesList.isEmpty()) {
            throw new IllegalStateException("No images found for this property.");
        }
        imagesRepository.deleteAll(imagesList);
    }

    @Value("${aws.s3.bucketName}")
    private String bucketName;
// delete the ALL property image from database or s3 bucket
    @Transactional
    public void deletePropertyImages(Long propertyId) {
        List<Images> imagesList = imagesRepository.findByPropertyId(propertyId);

        if (imagesList.isEmpty()) {
            throw new IllegalStateException("No images found for this property.");
        }

        for (Images image : imagesList) {
            String fileName = extractFileName(image.getUrl());

            // 🛑 Step 1: Delete image from S3
            amazonS3.deleteObject(bucketName, fileName);

            // ✅ Step 2: Delete image from database
            imagesRepository.delete(image);
        }
    }

    private String extractFileName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
    }
}