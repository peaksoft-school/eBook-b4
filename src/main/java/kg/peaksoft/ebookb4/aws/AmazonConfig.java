package kg.peaksoft.ebookb4.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AmazonConfig {

    @Bean
    public static AmazonS3Client s3(){
        AWSCredentials awsCredentials = new BasicAWSCredentials(
                AwsCredentials.AWS_ACCESSKEY.getAwsCredentials(),
                AwsCredentials.AWS_SECRETKEY.getAwsCredentials()
        );
        return (AmazonS3Client) AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }
}
