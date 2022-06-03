package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.aws.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/aws")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "This API for saving files {images, audios, electronic_books} to amazon S3 bucket")
public class FileApi {

    private final FileService fileService;

    @CrossOrigin
    @Operation(summary = "Upload files",description = "Upload files to aws")
    @PostMapping("/upload-file/{bookId}")
    public Map<String, String> uploadFile(@RequestBody MultipartFile firstPhoto,
                                                 @RequestBody MultipartFile secondPhoto,
                                                 @RequestBody MultipartFile thirdPhoto,
                                                 @RequestBody MultipartFile bookFile,
                                                 @RequestBody MultipartFile audioFragment,
                                                 @PathVariable Long bookId) {

            return fileService.uploadFile(firstPhoto, secondPhoto, thirdPhoto, bookFile, audioFragment, bookId);
    }

    @CrossOrigin
    @Operation(summary = "Delete files", description = "Delete files with key name")
    @DeleteMapping("/delete/{keyName}")
    public ResponseEntity<?> deleteFile(@PathVariable String keyName){
        fileService.deleteFile(keyName);
        return ResponseEntity.ok("File successfully deleted");
    }

    @CrossOrigin
    @Operation(summary = "Download file", description = "Download file with key name")
    @GetMapping("/download/{keyName}")
    public byte[] downloadFile(@PathVariable String keyName){
        return fileService.downloadFile(keyName);
    }

    @CrossOrigin
    @Operation(summary = "Update fileInformation",
            description = "You need to put book id with name of file " +
                    "for example {firstPhoto, secondPhoto, thirdPhoto, bookFile, audioFragment}, after you can update file information")
    @PostMapping("/fileInformation/{nameOfFile}/{bookId}")
    public LinkedHashMap<String, String> updateFileInformation(@RequestBody MultipartFile file,
                                                               @PathVariable String nameOfFile,
                                                               @PathVariable Long bookId){
        return fileService.updateFileInformation(file,nameOfFile, bookId);
    }
}