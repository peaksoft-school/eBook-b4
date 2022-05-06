package kg.peaksoft.ebookb4.api.Vendor.file;

import io.swagger.v3.oas.annotations.tags.Tag;
import kg.peaksoft.ebookb4.aws.enums.FolderName;
import kg.peaksoft.ebookb4.aws.model.FileInfo;
import kg.peaksoft.ebookb4.aws.service.FileService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/static")
@AllArgsConstructor
@PreAuthorize("permitAll()")
@Tag(name = "This API for saving files {images, audios, electronic_books} to amazon S3 bucket")
public class FileApi {

    private final FileService fileService;

    @PostMapping(
            path = "/upload/image",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public FileInfo uploadFile(@RequestParam MultipartFile file) {
        return fileService.uploadFile(FolderName.IMAGES, file);
    }

    @PostMapping("/upload/audio")
    public FileInfo uploadAudioFile(@RequestParam MultipartFile file) {
        return fileService.uploadFile(FolderName.AUDIO_FILES, file);
    }

    @PostMapping("/upload/pdf")
    public FileInfo uploadPDFFile(@RequestParam MultipartFile file) {
        return fileService.uploadFile(FolderName.PDF_FILES, file);
    }

    @GetMapping("/download/{fileId}")
    public byte[] downloadFile(@PathVariable Long fileId) {
        return fileService.downloadFile(fileId);
    }

    @DeleteMapping("/delete/{fileId}")
    public void deleteFile(@PathVariable Long fileId) {
        fileService.deleteFile(fileId);
    }
}
