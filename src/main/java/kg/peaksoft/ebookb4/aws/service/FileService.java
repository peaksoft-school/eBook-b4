package kg.peaksoft.ebookb4.aws.service;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import javax.transaction.Transactional;
import java.util.LinkedHashMap;

@Transactional
public interface FileService {
    LinkedHashMap<String, String> uploadFile(MultipartFile firstPhoto,
                                             MultipartFile secondPhoto,
                                             MultipartFile thirdPhoto,
                                             MultipartFile bookFile,
                                             MultipartFile audioFragment,
                                             Long bookId);

    void deleteFile(String keyName);

    byte[] downloadFile(String key);

}
