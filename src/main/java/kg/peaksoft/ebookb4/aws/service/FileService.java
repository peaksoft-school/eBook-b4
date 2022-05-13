package kg.peaksoft.ebookb4.aws.service;


import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedHashMap;

public interface FileService {
    LinkedHashMap<String, String> uploadFile(MultipartFile file1,
                                             MultipartFile file2,
                                             MultipartFile file3,
                                             Long bookId);

    void deleteFile(String keyName);;

}
