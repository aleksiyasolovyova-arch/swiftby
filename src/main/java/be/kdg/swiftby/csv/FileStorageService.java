package be.kdg.swiftby.csv;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {
    private static final String UPLOAD_DIR = "uploads";  // Directory to store files

    public String storeFile(MultipartFile file) {
        try {
            // Ensure directory exists
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            //getting file extension and ensuring it's a CSV
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if(!"csv".equalsIgnoreCase(extension)) {
                throw new IllegalArgumentException("Only CSV file format: " + extension);
            }

            //Defining the file path
            Path filePath = uploadDir.toPath().resolve(file.getOriginalFilename());

            //copy file to destination
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                    return "File uploaded successfully: " + filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + e.getMessage());
        }
    }
    public boolean isFileValid(String fileName) {
        File file = new File(UPLOAD_DIR + "/" + fileName);
        return file.exists() && file.length() > 0;
    }

    public File getFile(String fileName) {
        File file = new File(UPLOAD_DIR + "/" + fileName);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + fileName);
        }
        return file;
    }
}