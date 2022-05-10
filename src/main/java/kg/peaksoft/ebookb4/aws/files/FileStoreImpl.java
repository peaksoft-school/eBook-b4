package kg.peaksoft.ebookb4.aws.files;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class FileStoreImpl implements FileStore {

    private final AmazonS3 s3;

    @Autowired
    public FileStoreImpl(AmazonS3 s3) {
        this.s3 = s3;
    }

    @Override
    public void save(String path,
                     String fileName,
                     Optional<Map<String, String>> optionalMetadata,
                     InputStream inputStream) {

        ObjectMetadata objectMetadata = new ObjectMetadata();

        optionalMetadata.ifPresent(map -> {
            if (!map.isEmpty()) {
                map.forEach(objectMetadata::addUserMetadata);
            }
        });

        try {
            s3.putObject(path, fileName, inputStream, objectMetadata);
        } catch (AmazonServiceException e) {
            log.error("file {} cannot save to amazon s3 bucket", fileName);
            throw new IllegalStateException(
                    "failed to upload file to amazon s3 bucket"
            );
        }
    }

    @Override
    public byte[] download(String path, String key) {
        try {
            S3Object object = s3.getObject(path, key);
            S3ObjectInputStream objectContent = object.getObjectContent();
            return IOUtils.toByteArray(objectContent);
        } catch (AmazonServiceException | IOException amazonServiceException) {
            throw new AmazonServiceException (
                    String.format("failed to download file [%s] from amazon", key)
            );
        }
    }

    @Override
    public void delete(String filePath, String fileName) {
        try {
            s3.deleteObject(filePath, fileName);
        } catch (AmazonServiceException ex) {
            log.info("failed to delete file = {} from amazon s3 bucket", fileName);
            throw new AmazonServiceException (
                    String.format("failed to delete file [%s] from amazon", fileName)
            );
        }
    }
}
