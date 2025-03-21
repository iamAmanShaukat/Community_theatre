package project.community.theatre.service.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import project.community.theatre.constant.AppConstants;
import project.community.theatre.enums.ImageFormat;
import project.community.theatre.exception.InvalidImageException;
import project.community.theatre.service.ImageService;
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
public class ImageServiceImpl implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    /**
     * Processes the given MultipartFile and returns the URL of the processed image.
     *
     * The image is first validated to check if it's a valid image file. If not, an
     * {@link InvalidImageException} is thrown.
     *
     * The image is then resized and compressed locally using the
     * {@link Thumbnails} library.
     *
     * The processed file is then uploaded to Cloudinary using the
     * {@link Cloudinary} library.
     *
     * @param image the image to process
     * @return the URL of the processed image
     * @throws IOException if there is an error uploading the image
     */
    public String getImageUrl(MultipartFile image) throws IOException {
        return processImage(image);
    }

    /**
     * Uploads the given MultipartFile to Cloudinary and returns the URL of the uploaded image.
     *
     * @param file the image to upload
     * @return the URL of the uploaded image
     * @throws IOException if there is an error uploading the image
     */
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

    /**
     * Process the given MultipartFile and return the URL of the processed image.
     *
     * The image is first validated to check if it's a valid image file. If not, an
     * {@link InvalidImageException} is thrown.
     *
     * The image is then resized and compressed locally using the
     * {@link Thumbnails} library.
     *
     * The processed file is then uploaded to Cloudinary using the
     * {@link #uploadImage(MultipartFile)} method.
     *
     * @param image the image to process
     * @return the URL of the processed image
     * @throws IOException if there is an error processing the image
     * @throws InvalidImageException if the image is not a valid image file
     */
    private String processImage(MultipartFile image) {
        File tempFile = null;
        try {
            validateImage(image);

            // Generate a unique filename for temporary storage
            String fileName = UUID.randomUUID() + "." + getExtension(image.getOriginalFilename());
            tempFile = createTempFile(fileName);

            // Resize and compress the image locally
            Thumbnails.of(image.getInputStream())
                    .size(AppConstants.IMAGE_WIDTH, AppConstants.IMAGE_HEIGHT)
                    .outputQuality(AppConstants.IMAGE_QUALITY)
                    .toFile(tempFile);

            // Convert the processed file back to MultipartFile

            return uploadImage(new CustomMultipartFile(tempFile, image.getOriginalFilename()));

        } catch (InvalidImageException | IllegalArgumentException e) {
            log.error("Validation failed: {}", e.getMessage(), e);
            throw e;
        } catch (IOException e) {
            log.error("Failed to process the image", e);
            throw new RuntimeException("Failed to process the image", e);
        }finally {
            cleanupTempFile(tempFile);
        }
    }

    /**
     * Validates the given MultipartFile to ensure it is a valid image file.
     * The validation includes:
     * <ul>
     *     <li>Checking if the content type of the file is an image type</li>
     *     <li>Checking if the file size is less than the maximum allowed size (5MB)</li>
     *     <li>Checking if the file extension is one of the supported image formats</li>
     * </ul>
     * If any of the validation fails, an {@link InvalidImageException} is thrown.
     *
     * @param image the image to validate
     * @throws InvalidImageException if the image is not a valid image file
     */
    private void validateImage(MultipartFile image) {
        if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
            throw new InvalidImageException("Only image files are allowed");
        }
        if (image.getSize() > AppConstants.MAX_FILE_SIZE_BYTES) {
            throw new InvalidImageException("File size exceeds the limit of 5MB");
        }
        String extension = getExtension(image.getOriginalFilename());
        if (!ImageFormat.isSupported(extension)) {
            throw new InvalidImageException("Unsupported file format. Supported formats: " + ImageFormat.getSupportedFormatsAsString());
        }
    }


    /**
     * Creates a temporary file with the given filename.
     * The file is created in the system's default temporary directory.
     * The file name will be prefixed with "temp-" and suffixed with the given filename.
     * A log message at INFO level is written to indicate the creation of the file.
     * @param fileName the filename of the temporary file
     * @return the created temporary file
     * @throws IOException if an I/O error occurs
     */
    private File createTempFile(String fileName) throws IOException {
        File tempFile = File.createTempFile("temp-", "-" + fileName);
        log.info("Temporary file created: {}", tempFile.getAbsolutePath());
        return tempFile;
    }

    /**
     * Cleans up a temporary file.
     * <p>
     * If the given file is not null and exists, it is attempted to be deleted.
     * If the deletion is successful, a log message at INFO level is written.
     * If the deletion fails, a log message at WARN level is written.
     *
     * @param tempFile the temporary file to clean up
     */
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

    /**
     * Gets the file extension of the given filename.
     * The extension is returned in lower case.
     * If the given filename does not contain a dot, an {@link InvalidImageException} is thrown.
     * @param originalFilename the filename from which to get the extension
     * @return the file extension
     * @throws InvalidImageException if the filename does not contain a dot
     */
    private String getExtension(String originalFilename) {
        return Optional.ofNullable(originalFilename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1).toLowerCase())
                .orElseThrow(() -> new InvalidImageException("Invalid file format"));
    }
}