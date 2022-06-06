package kg.peaksoft.ebookb4.db.models.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AwsUploadResponse {
    private String urlForUpload;
    private String newUniqueNameForFile;
}
