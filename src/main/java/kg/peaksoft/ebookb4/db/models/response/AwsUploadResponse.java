package kg.peaksoft.ebookb4.db.models.response;

import lombok.*;

@Getter @Setter
public class AwsUploadResponse {
    private String urlForUpload;
    private String urlForView;
}
