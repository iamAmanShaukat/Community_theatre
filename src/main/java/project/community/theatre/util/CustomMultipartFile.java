package project.community.theatre.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Custom implementation of Spring's MultipartFile interface to handle file uploads.
 * This class wraps a File object and provides methods to retrieve file information and content.
 */
public class CustomMultipartFile implements MultipartFile {

    private final File file;
    private final String originalFilename;

    /**
     * Constructs a new CustomMultipartFile instance.
     *
     * @param file The underlying File object representing the uploaded file.
     * @param originalFilename The original filename provided by the client.
     */
    public CustomMultipartFile(File file, String originalFilename) {
        this.file = file;
        this.originalFilename = originalFilename;
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return "image/" + getExtension(originalFilename);
    }

    @Override
    public boolean isEmpty() {
        return file.length() == 0;
    }

    @Override
    public long getSize() {
        return file.length();
    }

    @Override
    public byte[] getBytes() throws IOException {
        return Files.readAllBytes(file.toPath());
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {
        Files.copy(file.toPath(), dest.toPath());
    }

    /**
     * Extracts the file extension from the given filename.
     *
     * @param filename The filename to extract the extension from.
     * @return The extracted file extension. If no extension is found, returns "jpg".
     */
    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "jpg";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}