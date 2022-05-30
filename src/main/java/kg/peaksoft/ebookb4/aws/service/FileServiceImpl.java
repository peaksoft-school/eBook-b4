package kg.peaksoft.ebookb4.aws.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import kg.peaksoft.ebookb4.aws.enums.BucketName;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class FileServiceImpl implements FileService {
    private AmazonS3Client awsS3Client;

    private final BookRepository bookRepository;

    @Override
    public LinkedHashMap<String, String> uploadFile(MultipartFile firstPhoto,
                                                    MultipartFile secondPhoto,
                                                    MultipartFile thirdPhoto,
                                                    MultipartFile bookFile,
                                                    MultipartFile audioFragment,
                                                    Long bookId) {

        Book bookById = bookRepository.getById(bookId);
        String firstPhotoExtension = StringUtils.getFilenameExtension(firstPhoto.getOriginalFilename());
        String secondPhotoExtension = StringUtils.getFilenameExtension(secondPhoto.getOriginalFilename());
        String thirdPhotoExtension = StringUtils.getFilenameExtension(thirdPhoto.getOriginalFilename());


        String keyOfFirstPhoto = "Images/" + UUID.randomUUID() + "." + firstPhotoExtension;
        String keyOfSecondPhoto = "Images/" + UUID.randomUUID() + "." + secondPhotoExtension;
        String keyOfThirdPhoto = "Images/" + UUID.randomUUID() + "." + thirdPhotoExtension;

        ObjectMetadata metaDataForFirstPhoto = new ObjectMetadata();
        metaDataForFirstPhoto.setContentLength(firstPhoto.getSize());
        metaDataForFirstPhoto.setContentType(firstPhoto.getContentType());

        ObjectMetadata metaDataForSecondPhoto = new ObjectMetadata();
        metaDataForSecondPhoto.setContentLength(secondPhoto.getSize());
        metaDataForSecondPhoto.setContentType(secondPhoto.getContentType());

        ObjectMetadata metaDataForThirdPhoto = new ObjectMetadata();
        metaDataForThirdPhoto.setContentLength(thirdPhoto.getSize());
        metaDataForThirdPhoto.setContentType(thirdPhoto.getContentType());


        try {
            awsS3Client.putObject(BucketName.AWS_BOOKS.getBucketName(), keyOfFirstPhoto, firstPhoto.getInputStream(), metaDataForFirstPhoto);
            awsS3Client.putObject(BucketName.AWS_BOOKS.getBucketName(), keyOfSecondPhoto, secondPhoto.getInputStream(), metaDataForSecondPhoto);
            awsS3Client.putObject(BucketName.AWS_BOOKS.getBucketName(), keyOfThirdPhoto, thirdPhoto.getInputStream(), metaDataForThirdPhoto);
            log.info("upload the files");
            log.info("name: {}", firstPhoto.getOriginalFilename());
            log.info("name: {}", secondPhoto.getOriginalFilename());
            log.info("name: {}", thirdPhoto.getOriginalFilename());

        } catch (IOException e) {
            log.error("an exception occurred while uploading the file");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occurred while uploading the file");
        }
        awsS3Client.setObjectAcl(BucketName.AWS_BOOKS.getBucketName(), keyOfFirstPhoto, CannedAccessControlList.PublicRead);
        awsS3Client.setObjectAcl(BucketName.AWS_BOOKS.getBucketName(), keyOfSecondPhoto, CannedAccessControlList.PublicRead);
        awsS3Client.setObjectAcl(BucketName.AWS_BOOKS.getBucketName(), keyOfThirdPhoto, CannedAccessControlList.PublicRead);

        if (bookById.getFileInformation().getFirstPhoto() == null ||
                !bookById.getFileInformation().getFirstPhoto().equals(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfFirstPhoto))) {
            if (bookById.getFileInformation().getKeyOfFirstPhoto() == null) {
                log.info("it's new first photo");
            } else {
                deleteFile(bookById.getFileInformation().getKeyOfFirstPhoto());
            }
            bookById.getFileInformation().setFirstPhoto(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfFirstPhoto));
        }
        if (bookById.getFileInformation().getSecondPhoto() == null ||
                !bookById.getFileInformation().getSecondPhoto().equals(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfSecondPhoto))) {
            if (bookById.getFileInformation().getKeyOfSecondPhoto() == null) {
                log.info("it's new second photo");
            } else {
                deleteFile(bookById.getFileInformation().getKeyOfSecondPhoto());
            }
        }
        if (bookById.getFileInformation().getThirdPhoto() == null ||
                !bookById.getFileInformation().getThirdPhoto().equals(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfThirdPhoto))) {
            if (bookById.getFileInformation().getKeyOfThirdPhoto() == null) {
                log.info("it's new third photo");
            } else {
                deleteFile(bookById.getFileInformation().getKeyOfThirdPhoto());
            }
        }

        bookById.getFileInformation().setKeyOfFirstPhoto(keyOfFirstPhoto);
        bookById.getFileInformation().setKeyOfSecondPhoto(keyOfSecondPhoto);
        bookById.getFileInformation().setKeyOfThirdPhoto(keyOfThirdPhoto);

        bookById.getFileInformation().setFirstPhoto(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfFirstPhoto));
        bookById.getFileInformation().setSecondPhoto(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfSecondPhoto));
        bookById.getFileInformation().setThirdPhoto(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfThirdPhoto));
        bookRepository.save(bookById);
        LinkedHashMap<String, String> response = new LinkedHashMap<>();
        response.put("file information Id ", String.valueOf(bookById.getFileInformation().getFileId()));
        response.put("first image", awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfFirstPhoto));
        response.put("second image", awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfSecondPhoto));
        response.put("third image", awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfThirdPhoto));
        if (bookById.getBookType().equals(BookType.EBOOK) && bookFile != null) {
            response.put("book file", uploadBookFile(bookFile, bookById));
        }
        if (bookById.getBookType().equals(BookType.AUDIOBOOK) && audioFragment != null && bookFile != null) {
            response.put("book file", uploadBookFile(bookFile, bookById));
            response.put("audio fragment", uploadAudioFragment(audioFragment, bookById));
        }
        return response;
    }

    public String uploadBookFile(MultipartFile bookFile, Book bookById) {
        String bookFileExtension = StringUtils.getFilenameExtension(bookFile.getOriginalFilename());
        String keyOfBookFile = "Book files/" + UUID.randomUUID() + "." + bookFileExtension;

        ObjectMetadata metaDataForBookFile = new ObjectMetadata();
        metaDataForBookFile.setContentLength(bookFile.getSize());
        metaDataForBookFile.setContentType(bookFile.getContentType());
        try {
            awsS3Client.putObject(BucketName.AWS_BOOKS.getBucketName(), keyOfBookFile, bookFile.getInputStream(), metaDataForBookFile);
            log.info("name: {}", bookFile.getOriginalFilename());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occurred while uploading the file");
        }
        awsS3Client.setObjectAcl(BucketName.AWS_BOOKS.getBucketName(), keyOfBookFile, CannedAccessControlList.PublicRead);
        bookById.getFileInformation().setKeyOfBookFile(keyOfBookFile);

        if (bookById.getFileInformation().getBookFile() == null ||
                !bookById.getFileInformation().getBookFile().equals(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfBookFile))) {
            if (bookById.getFileInformation().getKeyOfBookFile() == null) {
                log.info("it's new book file");
            } else {
                deleteFile(bookById.getFileInformation().getKeyOfBookFile());
            }
        }
        bookById.getFileInformation().setBookFile(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfBookFile));

        return awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfBookFile);
    }

    public String uploadAudioFragment(MultipartFile audioFragment, Book bookById) {
        String audioBookFragmentExtension = StringUtils.getFilenameExtension(audioFragment.getOriginalFilename());
        String keyOfAudioBookFragment = "Book files/" + UUID.randomUUID() + "." + audioBookFragmentExtension;

        ObjectMetadata metaDataForAudioBookFragment = new ObjectMetadata();
        metaDataForAudioBookFragment.setContentLength(audioFragment.getSize());
        metaDataForAudioBookFragment.setContentType(audioFragment.getContentType());
        try {
            awsS3Client.putObject(BucketName.AWS_BOOKS.getBucketName(), keyOfAudioBookFragment, audioFragment.getInputStream(), metaDataForAudioBookFragment);
            log.info("name: {}", audioFragment.getOriginalFilename());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "An exception occurred while uploading the file");
        }
        awsS3Client.setObjectAcl(BucketName.AWS_BOOKS.getBucketName(), keyOfAudioBookFragment, CannedAccessControlList.PublicRead);

        if (bookById.getBookType().equals(BookType.AUDIOBOOK)) {
            if (bookById.getAudioBook().getUrlFragment() == null ||
                    !bookById.getAudioBook().getUrlFragment().equals(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfAudioBookFragment))) {
                if (bookById.getAudioBook().getKeyOfFragment() == null) {
                    log.info("it's new audio fragment");
                } else {
                    deleteFile(bookById.getAudioBook().getKeyOfFragment());
                }
            }
        }

        if (bookById.getBookType().equals(BookType.AUDIOBOOK)) {
            bookById.getAudioBook().setKeyOfFragment(keyOfAudioBookFragment);
            bookById.getAudioBook().setUrlFragment(awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfAudioBookFragment));
        }

        return awsS3Client.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), keyOfAudioBookFragment);
    }

    @Override
    public void deleteFile(String keyName) {
        final DeleteObjectRequest deleteObjectRequest = new
                DeleteObjectRequest(BucketName.AWS_BOOKS.getBucketName(), keyName);
        awsS3Client.deleteObject(deleteObjectRequest);
        log.info("Successfully deleted");
    }

    @Override
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
