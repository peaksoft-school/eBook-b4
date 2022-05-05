package kg.peaksoft.ebookb4.aws.service;

import com.amazonaws.AmazonServiceException;
import kg.peaksoft.ebookb4.aws.enums.BucketName;
import kg.peaksoft.ebookb4.aws.enums.FolderName;
import kg.peaksoft.ebookb4.aws.exception.DoesNotExistsException;
import kg.peaksoft.ebookb4.aws.exception.InvalidFileException;
import kg.peaksoft.ebookb4.aws.files.FileStore;
import kg.peaksoft.ebookb4.aws.model.FileInfo;
import kg.peaksoft.ebookb4.aws.repo.FileRepository;
import kg.peaksoft.ebookb4.db.models.books.Book;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import kg.peaksoft.ebookb4.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;


@Service
@AllArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final FileStore fileStore;

    @Override
    public FileInfo uploadFile(FolderName folderName, MultipartFile multipartFile) {

        if (multipartFile.isEmpty()) {
            log.error("file must not be null {}", multipartFile.getSize());
            throw new InvalidFileException(
                    "cannot upload empty file [ " + multipartFile.getSize() + " ]"
            );
        }

        if (!getMimeTypes(folderName).contains(multipartFile.getContentType())) {
            log.error("file should be like {} this files", getMimeTypes(folderName));
            throw new InvalidFileException(
                    "file should be an " + folderName.getContentTypes()
            );
        }

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", multipartFile.getContentType());
        metadata.put("Content-Length", String.valueOf(multipartFile.getSize()));

        String fileName = String.format("%s/%s", UUID.randomUUID(), multipartFile.getOriginalFilename());
        try {
            fileStore.save(folderName.getPath(), fileName, Optional.of(metadata), multipartFile.getInputStream());
            log.info("successfully upload file to amazon s3 server :: file name = {}", fileName);
        } catch (AmazonServiceException | IOException e) {
            throw new AmazonServiceException(
                    "failed to save file to Amazon s3 server"
            );
        }
        return fileRepository.save(new FileInfo(null,folderName, fileName));
    }

    private Set<String> getMimeTypes(FolderName folderName) {
        return folderName.getMimeTypes(folderName.getContentTypes());
    }

    @Override
    public byte[] downloadFile(Long fileId) {
        FileInfo fileInfo = fileRepository.findById(fileId)
                .orElseThrow(() -> new InvalidFileException(
                        "CANNOT DOWNLOAD FILE \n reason: file does not exists"
                ));
        log.info("downloading file [{}]", fileInfo.getFileName());
        return fileStore.download(fileInfo.getFolderName().getPath(), fileInfo.getFileName());
    }

    @Override
    public void deleteFile(Long fileId) {
        FileInfo fileInfo = fileRepository.findById(fileId)
                .orElseThrow(() -> new InvalidFileException(
                        "file with id = " + fileId + " does not exists"
                ));
        try {
            log.warn("{}/{}", fileInfo.getFolderName().getPath(), fileInfo.getFileName());
            fileStore.delete(fileInfo.getFolderName().getPath(), fileInfo.getFileName());
            fileRepository.deleteById(fileInfo.getId());
            log.info("successfully deleted from database :: file name = {}", fileInfo.getFileName());
        } catch (AmazonServiceException e) {
            log.info("failed to delete file from amazon s3 bucket");
            throw new AmazonServiceException(
                    "failed to delete file from amazon s3 bucket"
            );
        }
    }

    @Override
    public FileInfo findById(Long id) {
        FileInfo fileInfo = fileRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("file info with id = {} does not exists", id);
                    throw new DoesNotExistsException(
                            "file info with id = " + id + " does not exists"
                    );
                });
        log.info("founded file info [{}]", fileInfo);
        return fileInfo;
    }

}
