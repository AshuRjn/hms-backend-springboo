package com.hms.utility;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class S3BucketService {

    private AmazonS3 amazonS3;

    public S3BucketService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    public String uploadFile(MultipartFile file, String bucketName) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload an empty file");
        }
        try (InputStream inputStream = file.getInputStream()) {
            // Set metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            // Upload file directly to S3 using InputStream
            amazonS3.putObject(bucketName, file.getOriginalFilename(), inputStream, metadata);

            // Return the file URL from S3
            return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload file", e);
        }
    }

}
