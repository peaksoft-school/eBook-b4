package kg.peaksoft.ebookb4.api.Vendor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.aws.service.FileService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/aws")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "This API for saving files {images, audios, electronic_books} to amazon S3 bucket")
public class FileApi {

    private final FileService fileService;

    @Operation(summary = "Upload files",description = "Upload files to aws")
    @PostMapping("/uploadFile/{bookId}")
    public Map<String, String> uploadAudioFile12(@RequestBody MultipartFile file1,
                                                 @RequestBody MultipartFile file2,
                                                 @RequestBody MultipartFile file3,
                                                 @PathVariable Long bookId) {
        Map<String, String> stringStringMap = fileService.uploadFile(file1, file2, file3, bookId);
        return stringStringMap;
    }

    @Operation(summary = "Delete files", description = "Delete files with key name")
    @DeleteMapping("/delete/{keyName}")
    public ResponseEntity<?> deleteFile(@PathVariable String keyName){
        fileService.deleteFile(keyName);
        return ResponseEntity.ok("File successfully deleted");
    }

    @Operation(summary = "Download file", description = "Download file with key name")
    @GetMapping("/download/{keyName}")
    public byte[] downloadFile(@PathVariable String keyName){
        return fileService.downloadFile(keyName);
    }
    
}
