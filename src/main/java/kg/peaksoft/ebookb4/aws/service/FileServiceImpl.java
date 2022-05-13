package kg.peaksoft.ebookb4.aws.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import kg.peaksoft.ebookb4.aws.enums.BucketName;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;


@Service
@AllArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private AmazonS3Client awsS3Client;

    private final BookRepository bookRepository;

    @Override
    public LinkedHashMap<String, String> uploadFile(MultipartFile file1,
                                                    MultipartFile file2,
                                                    MultipartFile file3,
                                                    Long bookId) {

        String filenameExtension1 = StringUtils.getFilenameExtension(file1.getOriginalFilename());
        String filenameExtension2 = StringUtils.getFilenameExtension(file2.getOriginalFilename());
        String filenameExtension3 = StringUtils.getFilenameExtension(file3.getOriginalFilename());

        String key1 = UUID.randomUUID().toString() + "." + filenameExtension1;
        String key2 = UUID.randomUUID().toString() + "." + filenameExtension2;
        String key3 = UUID.randomUUID().toString() + "." + filenameExtension3;

        ObjectMetadata metaData1 = new ObjectMetadata();
        metaData1.setContentLength(file1.getSize());
        metaData1.setContentType(file1.getContentType());

        ObjectMetadata metaData2 = new ObjectMetadata();
        metaData2.setContentLength(file2.getSize());
        metaData2.setContentType(file2.getContentType());

        ObjectMetadata metaData3 = new ObjectMetadata();
        metaData3.setContentLength(file3.getSize());
        metaData3.setContentType(file3.getContentType());

        try {
            awsS3Client.putObject("test-b4-ebook", key1, file1.getInputStream(), metaData1);
            log.info("upload the file");
            log.info("name: {}" , file1.getOriginalFilename());
        } catch (IOException e) {
            log.error("an exception occured while uploading the file");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occured while uploading the file");
        }
        awsS3Client.setObjectAcl("test-b4-ebook", key1, CannedAccessControlList.PublicRead);

        try {
            awsS3Client.putObject("test-b4-ebook", key2, file2.getInputStream(), metaData2);
            log.info("upload the file");
            log.info("name: {}" , file2.getOriginalFilename());
        } catch (IOException e) {
            log.error("an exception occured while uploading the file");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occured while uploading the file");
        }
        awsS3Client.setObjectAcl("test-b4-ebook", key2, CannedAccessControlList.PublicRead);

        try {
            awsS3Client.putObject("test-b4-ebook", key3, file3.getInputStream(), metaData3);
            log.info("upload the file");
            log.info("name: {}" , file3.getOriginalFilename());
        } catch (IOException e) {
            log.error("an exception occured while uploading the file");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occured while uploading the file");
        }
        awsS3Client.setObjectAcl("test-b4-ebook", key3, CannedAccessControlList.PublicRead);

        Book bookById = bookRepository.getById(bookId);

        log.info("its key {}", key1);
        log.info("its key {}", key2);
        log.info("its key {}", key3);

        bookById.getFileInformation().setKeyOfFirstPhoto(key1);
        bookById.getFileInformation().setKeyOfSecondPhoto(key2);
        bookById.getFileInformation().setKeyOfBookFile(key3);
        bookById.getFileInformation().setFirstPhoto(awsS3Client.getResourceUrl("test-b4-ebook", key1));
        bookById.getFileInformation().setSecondPhoto(awsS3Client.getResourceUrl("test-b4-ebook", key2));
        bookById.getFileInformation().setBookFile(awsS3Client.getResourceUrl("test-b4-ebook", key3));
        bookRepository.save(bookById);
        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("first image", awsS3Client.getResourceUrl("test-b4-ebook", key1));
        response.put("second image", awsS3Client.getResourceUrl("test-b4-ebook", key2));
        response.put("book file", awsS3Client.getResourceUrl("test-b4-ebook", key3));
        return response;
    }

    @Override
    public void deleteFile(String keyName) {
        final DeleteObjectRequest deleteObjectRequest = new
                DeleteObjectRequest(BucketName.AWS_BOOKS.getBucketName(), keyName);
        awsS3Client.deleteObject(deleteObjectRequest);
        log.info("Successfully deleted");
    }

    public byte[] downloadFile(String key) {
        try {
            S3Object object = awsS3Client.getObject(BucketName.AWS_BOOKS.getBucketName(), key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }
}
