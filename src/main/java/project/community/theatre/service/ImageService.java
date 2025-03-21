package project.community.theatre.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    /**
     * Takes a MultipartFile image and returns a URL of the image uploaded to Cloudinary
     * @param image the MultipartFile image to upload
     * @return the URL of the uploaded image
     * @throws IOException if there is an error uploading the image
     */
    String getImageUrl(MultipartFile image) throws IOException;
}
