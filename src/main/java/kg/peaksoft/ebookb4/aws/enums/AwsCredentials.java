package kg.peaksoft.ebookb4.aws.enums;

import lombok.Getter;

@Getter
public enum AwsCredentials {

    AWS_ACCESSKEY("AKIA3EDLM772OB45TPRY"),
    AWS_SECRETKEY("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"),
    AWS_BUCKET_NAME("ebook-b4-test");

    private final String awsCredentials;

    AwsCredentials(String bucketName) {
        this.awsCredentials = bucketName;
    }
}