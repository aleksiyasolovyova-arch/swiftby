package be.kdg.swiftby.csv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileStorageService {
    private static final String UPLOAD_DIR = "src/main/resources/uploads";

    public String storeFile(MultipartFile file) {
        try {
            File uploadDir = new File(UPLOAD_DIR);

            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            if (!"csv".equalsIgnoreCase(extension)) {
                throw new IllegalArgumentException("Only CSV file format: " + extension);
            }

            Path filePath = uploadDir.toPath().resolve(file.getOriginalFilename());

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "File uploaded successfully: " + filePath;
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
            log.error("File not found: {}", fileName);
            if (!file.exists()) {
                throw new RuntimeException("File not found: " + fileName);
            }
            log.info("File found: {}", fileName);
            return file;
        }
        return file;
    }

    public List<String[]> readFile(String fileName) {
        File file = new File(UPLOAD_DIR + "/" + fileName);
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + fileName);
        }

        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                data.add(values);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage());
        }
        return data;
    }
}
