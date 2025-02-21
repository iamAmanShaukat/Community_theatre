package project.community.theatre.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class CustomMultipartFile implements MultipartFile {

    private final File file;
    private final String originalFilename;

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

    private String getExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "jpg";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}