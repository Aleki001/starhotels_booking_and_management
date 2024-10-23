package com.alexkinyua.StarHotels.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class ImageService {

    // Define the folder where images will be saved
    private final String uploadDir = "/home/wababe/Pictures/StarHotel/";

    public String saveImage(MultipartFile image) throws IOException {
        // Ensure the directory exists
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();  // Create the directory if it doesn't exist
        }

        // Generate a unique filename for the image
        String originalFilename = image.getOriginalFilename();
        String uniqueFilename = System.currentTimeMillis() + "_" + originalFilename;

        // Build the full file path
        Path filePath = Paths.get(uploadDir + uniqueFilename);

        // Save the file locally
        Files.write(filePath, image.getBytes());

        // Return the relative file path (e.g., "uploads/123456_image.jpg")
        return uploadDir + uniqueFilename;
    }
}
