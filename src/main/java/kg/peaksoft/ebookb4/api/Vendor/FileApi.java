package kg.peaksoft.ebookb4.api.Vendor;

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

    @PostMapping("/uploadFile/{bookId}")
    public Map<String, String> uploadAudioFile12(@RequestBody MultipartFile file1,
                                                 @RequestBody MultipartFile file2,
                                                 @RequestBody MultipartFile file3,
                                                 @PathVariable Long bookId) {
        Map<String, String> stringStringMap = fileService.uploadFile(file1, file2, file3, bookId);
        return stringStringMap;
    }

    @DeleteMapping("/delete/{keyName}")
    public ResponseEntity<?> deleteFile(@PathVariable String keyName){
        fileService.deleteFile(keyName);
        return ResponseEntity.ok("File successfully deleted");
    }
    
}
