package core.service.impl;
import core.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {
    @Value("${app.upload-dir}")
    private String uploadDir;
    @Override
    public String save(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        Path dirPath = Paths.get(uploadDir);
        if (!Files.exists(dirPath)) {
            Files.createDirectories(dirPath);
        }
        String originalName = file.getOriginalFilename();
        String extension = "";
        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID() + extension;
        Path filePath = dirPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);
        return fileName;
    }
}
