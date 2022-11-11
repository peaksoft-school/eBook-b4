package kg.peaksoft.ebookb4.aws;

import lombok.Getter;

@Getter
public enum BucketName {

    AWS_BOOKS("ebook-b4-test");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

}