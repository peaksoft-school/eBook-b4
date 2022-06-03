package kg.peaksoft.ebookb4.aws.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
@Slf4j
public class FileStorageServceImpl implements FileStorageService{
    private final Path root = Paths.get("uploads");
    @Override
    public void init() {
        try {
            Files.createDirectory(root);
            log.info("Successful Creation");
        } catch (IOException e) {
            log.error("Un Successful Creation", e);
//            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }
}
