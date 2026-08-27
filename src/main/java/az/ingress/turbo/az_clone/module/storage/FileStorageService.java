package az.ingress.turbo.az_clone.module.storage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    private static final List<String> ALLOWED_TYPES = List.of("image/jpeg", "image/png", "image/webp");
    private static final long MAX_SIZE = 5 * 1024 * 1024;

    @Value("${app.upload.dir:uploads/cars}")
    private String uploadDir;

    public String store(MultipartFile file) {
        validate(file);
        try {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID() + "." + extension;

            Path targetDir = Paths.get(uploadDir);
            Files.createDirectories(targetDir);

            Path targetPath = targetDir.resolve(newFileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

            return newFileName;
        } catch (IOException e) {
            throw new RuntimeException("Fayl yüklənərkən xəta: " + e.getMessage(), e);
        }
    }

    private void validate(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Boş fayl göndərilə bilməz");
        }
        if (!ALLOWED_TYPES.contains(file.getContentType())) {
            throw new IllegalArgumentException("Yalnız jpg/png/webp formatına icazə verilir");
        }
        if (file.getSize() > MAX_SIZE) {
            throw new IllegalArgumentException("Fayl ölçüsü 5MB-dan böyük ola bilməz");
        }
    }
}