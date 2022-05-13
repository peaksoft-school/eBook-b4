package kg.peaksoft.ebookb4.aws.enums;

import lombok.Getter;

@Getter
public enum BucketName {

    AWS_BOOKS("test-b4-ebook");

    private final String bucketName;

    BucketName( String bucketName) {
        this.bucketName = bucketName;
    }
}