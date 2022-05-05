package kg.peaksoft.ebookb4.aws.service;

import kg.peaksoft.ebookb4.aws.enums.FolderName;
import kg.peaksoft.ebookb4.aws.model.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    FileInfo uploadFile(FolderName folderName, MultipartFile multipartFile);

    byte[] downloadFile(Long fileId);

    void deleteFile(Long fileId);

    FileInfo findById(Long id);

}
