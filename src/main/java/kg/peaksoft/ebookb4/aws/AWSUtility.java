package kg.peaksoft.ebookb4.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.enums.BookType;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Component
public class AWSUtility {

    private final String accessKey = AwsCredentials.AWS_ACCESSKEY.getAwsCredentials();
    private final String secretKey = AwsCredentials.AWS_SECRETKEY.getAwsCredentials();
    private final String bucketName = AwsCredentials.AWS_BUCKET_NAME.getAwsCredentials();
    private final Region region = Region.EU_CENTRAL_1;
    private final AmazonS3Client amazonS3Client;
    private final BookRepository bookRepository;

    @Autowired
    public AWSUtility(AmazonS3Client amazonS3Client, BookRepository bookRepository) {
        this.amazonS3Client = amazonS3Client;
        this.bookRepository = bookRepository;
    }

    AwsCredentialsProvider credentialsProvider =
            StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey,
                    secretKey));


    public String urlOfFile(String fileName) {
        amazonS3Client.getResourceUrl(bucketName, fileName);
        return amazonS3Client.getResourceUrl(bucketName, fileName);
    }

    public String getUrlForUploadFile(Long bookId, String abstractNameOfFile, String file) {
        Book bookById = bookRepository.getById(bookId);
        String nameOfFile = chekAbstractName(abstractNameOfFile) + UUID.randomUUID() + "." + file;
        if (abstractNameOfFile.equals("firstPhoto")) {
            if (bookById.getFileInformation().getFirstPhoto() == null) {
                bookById.getFileInformation().setKeyOfFirstPhoto(nameOfFile);
                bookById.getFileInformation().setFirstPhoto(urlOfFile(nameOfFile));
                log.info("It's new photo in - {}", abstractNameOfFile);
            } else {
                deleteFile(bookById.getFileInformation().getKeyOfFirstPhoto());
                bookById.getFileInformation().setKeyOfFirstPhoto(nameOfFile);
                bookById.getFileInformation().setFirstPhoto(urlOfFile(nameOfFile));
            }
        }
        if (abstractNameOfFile.equals("secondPhoto")) {
            if (bookById.getFileInformation().getSecondPhoto() == null) {
                bookById.getFileInformation().setKeyOfSecondPhoto(nameOfFile);
                bookById.getFileInformation().setSecondPhoto(urlOfFile(nameOfFile));
                log.info("It's new photo in - {}", abstractNameOfFile);
            } else {
                deleteFile(bookById.getFileInformation().getKeyOfSecondPhoto());
                bookById.getFileInformation().setKeyOfSecondPhoto(nameOfFile);
                bookById.getFileInformation().setSecondPhoto(urlOfFile(nameOfFile));
            }
        }
        if (abstractNameOfFile.equals("thirdPhoto")) {
            if (bookById.getFileInformation().getThirdPhoto() == null) {
                bookById.getFileInformation().setKeyOfThirdPhoto(nameOfFile);
                bookById.getFileInformation().setThirdPhoto(urlOfFile(nameOfFile));
                log.info("It's new photo in - {}", abstractNameOfFile);
            } else {
                deleteFile(bookById.getFileInformation().getKeyOfThirdPhoto());
                bookById.getFileInformation().setKeyOfThirdPhoto(nameOfFile);
                bookById.getFileInformation().setThirdPhoto(urlOfFile(nameOfFile));
            }
        }
        if (abstractNameOfFile.equals("bookFile")) {
            if (bookById.getFileInformation().getBookFile() == null) {
                bookById.getFileInformation().setBookFile(nameOfFile);
                bookById.getFileInformation().setKeyOfBookFile(urlOfFile(nameOfFile));
                log.info("It's new book file in - {}", abstractNameOfFile);
            } else {
                deleteFile(bookById.getFileInformation().getKeyOfBookFile());
                bookById.getFileInformation().setBookFile(nameOfFile);
                bookById.getFileInformation().setKeyOfBookFile(urlOfFile(nameOfFile));
            }
        }
        if (abstractNameOfFile.equals("audioBookFile")) {
            if (bookById.getBookType().equals(BookType.AUDIOBOOK)) {
                if (bookById.getFileInformation().getBookFile() == null) {
                    bookById.getFileInformation().setBookFile(nameOfFile);
                    bookById.getFileInformation().setKeyOfBookFile(urlOfFile(nameOfFile));
                    log.info("It's new audio file in - {}", abstractNameOfFile);
                } else {
                    deleteFile(bookById.getFileInformation().getKeyOfBookFile());
                    bookById.getFileInformation().setBookFile(nameOfFile);
                    bookById.getFileInformation().setKeyOfBookFile(urlOfFile(nameOfFile));
                }
            }
        }
        if (abstractNameOfFile.equals("audioFragment")) {
            if (bookById.getAudioBook().getUrlFragment() == null) {
                bookById.getAudioBook().setKeyOfFragment(nameOfFile);
                bookById.getAudioBook().setUrlFragment(urlOfFile(nameOfFile));
                log.info("It's new audio file in - {}", abstractNameOfFile);
            } else {
                deleteFile(bookById.getAudioBook().getKeyOfFragment());
                bookById.getAudioBook().setKeyOfFragment(nameOfFile);
                bookById.getAudioBook().setUrlFragment(urlOfFile(nameOfFile));
            }
        }
        bookRepository.save(bookById);
        S3Presigner s3Presigner = S3Presigner.builder()
                .credentialsProvider(credentialsProvider)
                .region(region).build();

        final String fileName = nameOfFile;
        log.info("file name= {}", fileName);
        PresignedPutObjectRequest presignedRequest =
                s3Presigner.presignPutObject(r -> r.signatureDuration(Duration.ofMinutes(5))
                        .putObjectRequest(por -> por.bucket(bucketName)
                                .key(fileName)));
        return presignedRequest.url().toString();
    }


    public byte[] downloadFile(String key) {
        try {
            S3Object object = amazonS3Client.getObject(AwsCredentials.AWS_BUCKET_NAME.getAwsCredentials(), key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException e) {
            throw new IllegalStateException("Failed to download the file", e);
        }
    }

    private void deleteFile(String keyName) {
        final DeleteObjectRequest deleteObjectRequest = new
                DeleteObjectRequest(bucketName, keyName);
        amazonS3Client.deleteObject(deleteObjectRequest);
        log.info("Successfully deleted");
    }

    public String chekAbstractName(String abstractName) {
        if (abstractName.equals("firstPhoto")) {
            return "Images/";
        }
        if (abstractName.equals("secondPhoto")) {
            return "Images/";
        }
        if (abstractName.equals("thirdPhoto")) {
            return "Images/";
        }
        if (abstractName.equals("bookFile")) {
            return "Book files/";
        }
        if (abstractName.equals("audioBookFile")) {
            return "Audio files/";
        }
        if (abstractName.equals("audioFragment")) {
            return "Audio files/";
        }
        return "";
    }

}
