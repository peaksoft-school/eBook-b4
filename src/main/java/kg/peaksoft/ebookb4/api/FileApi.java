package kg.peaksoft.ebookb4.api;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.aws.enums.AWSUtility;
import kg.peaksoft.ebookb4.aws.enums.BucketName;
import kg.peaksoft.ebookb4.db.models.response.AwsResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import kg.peaksoft.ebookb4.aws.service.FileService;
//import lombok.AllArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileNotFoundException;
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/aws")
@AllArgsConstructor
//@PreAuthorize("hasRole('ROLE_VENDOR')")
@PreAuthorize("permitAll()")
@Tag(name = "This API for saving files {images, audios, electronic_books} to amazon S3 bucket")
@Slf4j
public class FileApi {

    @Autowired
    private AWSUtility awsUtility;
//
//    private final FileService fileService;
//
//    @Operation(summary = "Upload files",description = "Upload files to aws")
//    @PostMapping("/upload-file/{bookId}")
//    public Map<String, String> uploadFile(@RequestBody MultipartFile firstPhoto,
//                                                 @RequestBody MultipartFile secondPhoto,
//                                                 @RequestBody MultipartFile thirdPhoto,
//                                                 @RequestBody MultipartFile bookFile,
//                                                 @RequestBody MultipartFile audioFragment,
//                                                 @PathVariable Long bookId) {
//
//            return fileService.uploadFile(firstPhoto, secondPhoto, thirdPhoto, bookFile, audioFragment, bookId);
//    }
//
//    @Operation(summary = "Delete files", description = "Delete files with key name")
//    @DeleteMapping("/delete/{keyName}")
//    public ResponseEntity<?> deleteFile(@PathVariable String keyName){
//        fileService.deleteFile(keyName);
//        return ResponseEntity.ok("File successfully deleted");
//    }
//
//    @Operation(summary = "Download file", description = "Download file with key name")
//    @GetMapping("/download/{keyName}")
//    public byte[] downloadFile(@PathVariable String keyName){
//        return fileService.downloadFile(keyName);
//    }
//
//
//    @Operation(summary = "Update fileInformation",
//            description = "You need to put book id with name of file " +
//                    "for example {firstPhoto, secondPhoto, thirdPhoto, bookFile, audioFragment}, after you can update file information")
//    @PostMapping("/fileInformation/{nameOfFile}/{bookId}")
//    public LinkedHashMap<String, String> updateFileInformation(@RequestBody MultipartFile file,
//                                                               @PathVariable String nameOfFile,
//                                                               @PathVariable Long bookId){
//        return fileService.updateFileInformation(file,nameOfFile, bookId);
//    }
//
//    @GetMapping("/putImage/{fileName}")
//    public AwsResponse generatePresidedPutUrl(@PathVariable String fileName){
//        AwsResponse awsResponse = new AwsResponse();
//            String resignedPutUrl = awsUtility.generatePresignedPutUrl(fileName);
//            awsResponse.setUrl(resignedPutUrl);
//            awsResponse.setKey(fileName);
//            return awsResponse;
//    }
//
//    @GetMapping("/getImage/{fileName}")
//    public ResponseEntity<String> generatePresignedGetUrl(@PathVariable String fileName) {
//        String getSignedUrl = awsUtility.generatePresignedGetUrl(fileName);
//        return ResponseEntity.ok().body(getSignedUrl);
//    }



//    @GetMapping("/getImage/{articleId}")
//    public ResponseEntity<String> generatePresignedGetUrl(@PathVariable int articleId) {
//        Article article = articleRepository.findById(new Long(articleId))
//                .orElseThrow(() -> new EntityNotFoundException("No such article was found"));
//        String getSignedUrl = AWSUtility.generatePresignedGetUrl(article.getImageUrl());
//        return ResponseEntity.ok().body(getSignedUrl);
//    }

    @GetMapping("/upload/{keyName}")
    public void uploadFile(@PathVariable String keyName) {
        S3Presigner s3Presigner = awsUtility.s3Presigner();
        awsUtility.getPresignedUrl(s3Presigner, BucketName.AWS_BOOKS.getBucketName(), keyName);
    }
    @PostMapping("/sign/{fileName}")
    public String uploadSign(@PathVariable String fileName) {
        return awsUtility.signBucket(BucketName.AWS_BOOKS.getBucketName(),fileName);
    }

    @GetMapping("/get/{fileName}")
    public String getUrlOfFile(@PathVariable String fileName){
        return awsUtility.urlOfFile(fileName);
    }








}
