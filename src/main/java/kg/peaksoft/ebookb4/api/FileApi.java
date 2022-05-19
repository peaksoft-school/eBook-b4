package kg.peaksoft.ebookb4.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.aws.service.FileService;
import kg.peaksoft.ebookb4.db.models.entity.Book;
import kg.peaksoft.ebookb4.db.models.response.BookResponse;
import kg.peaksoft.ebookb4.db.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/aws")
@AllArgsConstructor
@PreAuthorize("hasRole('ROLE_VENDOR')")
@Tag(name = "This API for saving files {images, audios, electronic_books} to amazon S3 bucket")
public class FileApi {

    private final FileService fileService;

    @Operation(summary = "Upload files",description = "Upload files to aws")
    @PostMapping("/upload-file/{bookId}")
    public Map<String, String> uploadFile(@RequestBody MultipartFile firstPhoto,
                                                 @RequestBody MultipartFile secondPhoto,
                                                 @RequestBody MultipartFile bookFile,
                                                 @PathVariable Long bookId) {
        return fileService.uploadFile(firstPhoto, secondPhoto, bookFile, bookId);
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
