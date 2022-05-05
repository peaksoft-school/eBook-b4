package kg.peaksoft.ebookb4.aws.enums;

import lombok.AllArgsConstructor;
import org.apache.http.entity.ContentType;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static kg.peaksoft.ebookb4.aws.enums.BucketName.AWS_BOOKS;
import static org.apache.http.entity.ContentType.*;

@AllArgsConstructor
public enum FolderName {

    IMAGES(
            String.format("%s/%s", AWS_BOOKS.getBucketName(), "IMAGES"),
            new HashSet<>(Arrays.asList(IMAGE_PNG, IMAGE_JPEG, IMAGE_SVG, IMAGE_GIF))
            ),

    AUDIO_FILES(
            String.format("%s/%s", AWS_BOOKS.getBucketName(), "AUDIO_FILES"),
            new HashSet<>(Arrays.asList(
    create("audio/basic"),
    create("audio/mpeg"),
    create("audio/mp3"),
    create("audio/m4a"),
    create("audio/vnd.wav")))
            ),
    PDF_FILES(
            String.format("%s/%s", AWS_BOOKS.getBucketName(), "PDF_FILES"),
            new HashSet<>(Arrays.asList(
            ContentType.create("application/pdf")
            ))
            );

    private String path;

    private Set<ContentType> contentTypes;

    public String getPath() {
        return path;
    }

    public Set<ContentType> getContentTypes() {
        return contentTypes;
    }

    public Set<String> getMimeTypes(Set<ContentType> contentTypes) {
        return contentTypes.stream().map(ContentType::getMimeType)
                .collect(Collectors.toSet());
    }
}
