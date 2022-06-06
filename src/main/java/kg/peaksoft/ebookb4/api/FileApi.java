package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.aws.enums.AWSUtility;
import kg.peaksoft.ebookb4.db.models.response.AwsUploadResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/aws")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "This API for saving files {images, audios, electronic_books} to amazon S3 bucket")
@Slf4j
public class FileApi {

    @Autowired
    private AWSUtility awsUtility;

    @Operation(summary = "Get url for view")
    @GetMapping("/urlForUpload/{bookId}/{abstractNameOfFile}/{keyName}")
    public AwsUploadResponse uploadSign(@PathVariable String abstractNameOfFile,
                                        @PathVariable Long bookId,
                                        @PathVariable String keyName) {
        return awsUtility.getUrlForUploadFile(bookId, abstractNameOfFile, keyName);
    }

    @Operation(summary = "Get url for upload file")
    @GetMapping("/get/{fileName}")
    public String getUrlOfFile(@PathVariable String fileName) {
        return awsUtility.urlOfFile(fileName);
    }

    @Operation(summary = "Download file", description = "Download file with key name")
    @GetMapping("/download/{keyName}")
    public byte[] downloadFile(@PathVariable String keyName) {
        return awsUtility.downloadFile(keyName);
    }

}
