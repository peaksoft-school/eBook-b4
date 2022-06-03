package kg.peaksoft.ebookb4.aws.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileStorageService {
    void init();
    void save(MultipartFile file);
}
