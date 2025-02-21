package project.community.theatre.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.enums.ImageFormat;
import project.community.theatre.exceptionHandler.InvalidImageException;
import project.community.theatre.util.CustomMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ImageService {

    private static final long MAX_FILE_SIZE_BYTES = 5 * 1024 * 1024; // 5MB
    private static final int IMAGE_WIDTH = 800;
    private static final int IMAGE_HEIGHT = 600;
    private static final double IMAGE_QUALITY = 1.0; // 100% quality

    @Autowired
    private Cloudinary cloudinary;

    public String getImageUrl(MultipartFile image) throws IOException {
        MultipartFile processedImage = processImage(image);
        return uploadImage(processedImage);
    }

    private String uploadImage(MultipartFile file) throws IOException {
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            return Optional.ofNullable(uploadResult.get("url"))
                    .map(String::valueOf)
                    .orElseThrow(() -> new IOException("Failed to retrieve image URL from Cloudinary"));
        } catch (Exception e) {
            log.error("Failed to upload image to Cloudinary: {}", e.getMessage(), e);
            throw new IOException("Failed to upload image to Cloudinary: " + e.getMessage(), e);
        }
    }

    private MultipartFile processImage(MultipartFile image) {
        File tempFile = null;
        try {
            validateImage(image);

            // Generate a unique filename for temporary storage
            String fileName = UUID.randomUUID() + "." + getExtension(image.getOriginalFilename());
            tempFile = createTempFile(fileName);

            // Resize and compress the image locally
            Thumbnails.of(image.getInputStream())
                    .size(IMAGE_WIDTH, IMAGE_HEIGHT)
                    .outputQuality(IMAGE_QUALITY)
                    .toFile(tempFile);

            // Convert the processed file back to MultipartFile
            return new CustomMultipartFile(tempFile, image.getOriginalFilename());

        } catch (InvalidImageException | IllegalArgumentException e) {
            log.error("Validation failed: {}", e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error("Failed to process the image", e);
            throw new RuntimeException("Failed to process the image", e);
        } finally {
            cleanupTempFile(tempFile);
        }
    }

    private void validateImage(MultipartFile image) {
        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            throw new InvalidImageException("Only image files are allowed");
        }
        if (image.getSize() > MAX_FILE_SIZE_BYTES) {
            throw new InvalidImageException("File size exceeds the limit of 5MB");
        }
        String extension = getExtension(image.getOriginalFilename());
        if (!ImageFormat.isSupported(extension)) {
            throw new InvalidImageException("Unsupported file format. Supported formats: " + ImageFormat.getSupportedFormatsAsString());
        }
    }


    private File createTempFile(String fileName) throws IOException {
        File tempFile = File.createTempFile("temp-", "-" + fileName);
        log.info("Temporary file created: {}", tempFile.getAbsolutePath());
        return tempFile;
    }

    private void cleanupTempFile(File tempFile) {
        if (tempFile != null && tempFile.exists()) {
            try {
                Files.delete(tempFile.toPath());
                log.info("Temporary file deleted: {}", tempFile.getAbsolutePath());
            } catch (IOException e) {
                log.warn("Failed to delete temporary file: {}", tempFile.getAbsolutePath(), e);
            }
        }
    }

    private String getExtension(String originalFilename) {
        return Optional.ofNullable(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1).toLowerCase())
                .orElseThrow(() -> new InvalidImageException("Invalid file format"));
    }
}