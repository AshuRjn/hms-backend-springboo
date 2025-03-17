package com.hms.utility;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class PdfUploaderS3 {

    // code for upload pdf to s3 account
    private final AmazonS3 s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    public PdfUploaderS3(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadPdfToS3(String filePath, String fileName) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + filePath);
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("application/pdf");
            metadata.setContentLength(file.length());

            s3Client.putObject(bucketName, fileName, fileInputStream, metadata);

            return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Error uploading PDF file to S3", e);
        }
    }
}
