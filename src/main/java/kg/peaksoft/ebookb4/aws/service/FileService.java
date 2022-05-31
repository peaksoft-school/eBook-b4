package kg.peaksoft.ebookb4.aws.service;



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

//    LinkedHashMap<String, String> updateFileInformation(MultipartFile firstPhoto,
//                                                        MultipartFile secondPhoto,
//                                                        MultipartFile thirdPhoto,
//                                                        MultipartFile bookFile,
//                                                        MultipartFile audioFragment,
//                                                        Long bookId);

}
