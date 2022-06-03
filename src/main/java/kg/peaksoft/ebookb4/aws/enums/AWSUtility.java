package kg.peaksoft.ebookb4.aws.enums;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.utils.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.time.Duration;

@Slf4j
@Component
public class AWSUtility {
    private static final Regions clientRegion = Regions.EU_CENTRAL_1;

    // TODO!! create a property reader
    private static final String bucketName = "ebook-b4-test";

    private AmazonS3Client amazonS3;

    @Autowired
    public AWSUtility(AmazonS3Client amazonS3) {
        this.amazonS3 = amazonS3;
    }


    public String urlOfFile(String fileName){
        amazonS3.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), fileName);
        return amazonS3.getResourceUrl(BucketName.AWS_BOOKS.getBucketName(), fileName);
    }


//    private static final AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
//            .withCredentials(new ProfileCredentialsProvider())
//            .withRegion(clientRegion)
//            .build();

//    public  String generatePresignedPutUrl(String fileName) {
//        try {
//            // Set the pre-signed URL to expire after 10 mins.
//            java.util.Date expiration = new java.util.Date();
//            long expTimeMillis = expiration.getTime();
//            expTimeMillis += 1000 * 60 * 10;
//            expiration.setTime(expTimeMillis);
//            // Generate the pre-signed URL
//            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucketName, fileName)
//                    .withMethod(HttpMethod.PUT)
//                    .withExpiration(expiration);
//            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
//            log.info("pre-signed URL for PUT operation has been generated.");
//            return url.toString();
//        } catch (AmazonServiceException e) {
//            // The call was transmitted successfully, but Amazon S3 couldn't process
//            // it, so it returned an error response.
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            // Amazon S3 couldn't be contacted for a response, or the client
//            // couldn't parse the response from Amazon S3.
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        log.error("URL could not be generated");
//        return null;
//    }
//
//
//    public String generatePresignedGetUrl(String fileName) {
//        try {
//            // Set the presigned URL to expire after 10 mins.
//            java.util.Date expiration = new java.util.Date();
//            long expTimeMillis = expiration.getTime();
//            expTimeMillis += 1000 * 60 * 10;
//            expiration.setTime(expTimeMillis);
//
//            // Generate the presigned URL.
//            GeneratePresignedUrlRequest generatePresignedUrlRequest =
//                    new GeneratePresignedUrlRequest(bucketName, fileName)
//                            .withMethod(HttpMethod.GET)
//                            .withExpiration(expiration);
//
//            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
//            log.info("pre-signed URL for GET operation has been generated.");
//            return amazonS3.getResourceUrl(bucketName,fileName);
//        } catch (AmazonServiceException e) {
//            // The call was transmitted successfully, but Amazon S3 couldn't process
//            // it, so it returned an error response.
//            e.printStackTrace();
//        } catch (SdkClientException e) {
//            // Amazon S3 couldn't be contacted for a response, or the client
//            // couldn't parse the response from Amazon S3.
//            e.printStackTrace();
//        }
//        return null;
//    }
;
    AwsCredentialsProvider credentialsProvider =
            StaticCredentialsProvider.create(AwsBasicCredentials.create("AKIA3EDLM772OB45TPRY",
                    "j5wKMRw9dKIqRbqeAWAT1cvYvdvH79AsVsT8bmzd"));

    public String signBucket(String bucketName, String file) {
//        try {
//            PutObjectRequest objectRequest = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(file.getOriginalFilename())
//                    .contentLength(file.getSize())
//                    .contentType(file.getContentType())
//                    .build();
//
//            PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
//                    .signatureDuration(Duration.ofMinutes(10))
//                    .putObjectRequest(objectRequest)
//                    .build();
//
//            PresignedPutObjectRequest presignedRequest = presigner.presignPutObject(presignRequest);
//            String myURL = presignedRequest.url().toString();
//            System.out.println("Presigned URL to upload a file to: " + myURL);
//            System.out.println("Which HTTP method needs to be used when uploading a file: " +
//                    presignedRequest.httpRequest().method());
//
//            // Загрузите содержимое в корзину Amazon S3, используя этот URL-адрес.
//            URL url = presignedRequest.url();
//            System.out.println(url);
//

//           // Создайте соединение и используйте его для загрузки нового объекта, используя предварительно указанный URL-адрес.
////            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
////            connection.setDoOutput(true);
////            connection.setRequestProperty("Content-Type","text/plain");
////            connection.setRequestMethod("PUT");
////            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
////            out.write("This text was uploaded as an object by using a presigned URL.");
////            System.out.println(out.getEncoding());
////            out.close();
////
////            connection.disconnect();
////            connection.getResponseCode();
////            System.out.println("HTTP response code is " + connection.getResponseCode());
////
////        } catch (S3Exception | IOException e) {
////            e.getStackTrace();
////        }

// Create an S3Presigner using the default region and credentials.
        // This is usually done at application startup, because creating a presigner can be expensive.
//        S3Presigner presigner = S3Presigner.create();

//        // Create a GetObjectRequest to be pre-signed
//        GetObjectRequest getObjectRequest =
//                GetObjectRequest.builder()
//                        .bucket(bucketName)
//                        .key(file.getOriginalFilename())
//                        .build();
//
//        // Create a GetObjectPresignRequest to specify the signature duration
//        GetObjectPresignRequest getObjectPresignRequest =
//                GetObjectPresignRequest.builder()
//                        .signatureDuration(Duration.ofMinutes(10))
//                        .getObjectRequest(getObjectRequest)
//                        .build();
//
//        // Generate the presigned request
//        PresignedGetObjectRequest presignedGetObjectRequest =
//                presigner.presignGetObject(getObjectPresignRequest);
//
//        // Log the presigned URL, for example.
//        System.out.println("Presigned URL: " + presignedGetObjectRequest.url());
//
//
//        try{
//            // Создайте соединение и используйте его для загрузки нового объекта, используя предварительно указанный URL-адрес.
//            HttpURLConnection connection = (HttpURLConnection) presignedGetObjectRequest.url().openConnection();
//            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type","text/plain");
//            connection.setRequestMethod("PUT");
//            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//            out.write("This text was uploaded as an object by using a presigned URL.ewagtrwtarewter");
//            System.out.println(out.getEncoding());
//            out.close();
//
//            connection.disconnect();
//            connection.getResponseCode();
//            System.out.println("HTTP response code is " + connection.getResponseCode());
//
//        } catch (S3Exception | IOException e) {
//            e.getStackTrace();
//        }

//         It is recommended to close the S3Presigner when it is done being used, because some credential
//         providers (e.g. if your AWS profile is configured to assume an STS role) require system resources
//         that need to be freed. If you are using one S3Presigner per application (as recommended), this
//         usually is not needed.
//        presigner.close();
//        This is also now supported for S3's PutObject. Example here.

//        S3Presigner presigner = S3Presigner.create();
        S3Presigner s3Presigner = S3Presigner.builder()
                .credentialsProvider(credentialsProvider)
                .region(Region.EU_CENTRAL_1).build();

        PresignedPutObjectRequest presignedRequest =
                s3Presigner.presignPutObject(r -> r.signatureDuration(Duration.ofMinutes(5))
                        .putObjectRequest(por -> por.bucket(bucketName)
                                .key(file)));
        String myUrl = presignedRequest.url().toString();

        System.out.println("Pre-signed URL to upload a file to: " +
                presignedRequest.url());
        System.out.println("Which HTTP method needs to be used when uploading a file: " +
                presignedRequest.httpRequest().method());
        System.out.println("Which headers need to be sent with the upload: " +
                presignedRequest.signedHeaders());
        s3Presigner.close();

        return myUrl;
    }


    public void getPresignedUrl(S3Presigner presigner, String bucketName, String keyName ) {
        try {
            GetObjectRequest getObjectRequest =
                    GetObjectRequest.builder()
                            .bucket(bucketName)
                            .key(keyName)
                            .build();

            GetObjectPresignRequest getObjectPresignRequest =  GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(10))
                    .getObjectRequest(getObjectRequest)
                    .build();

            // Сгенерируйте предварительно подписанный запрос
            PresignedGetObjectRequest presignedGetObjectRequest =
                    presigner.presignGetObject(getObjectPresignRequest);

            //Зарегистрируйте указанный URL-адрес
            System.out.println("Presigned URL: " + presignedGetObjectRequest.url());

            HttpURLConnection connection = (HttpURLConnection) presignedGetObjectRequest.url().openConnection();
            presignedGetObjectRequest.httpRequest().headers().forEach((header, values) -> {
                values.forEach(value -> {
                    connection.addRequestProperty(header, value);
                });
            });

            // Отправьте любую полезную нагрузку запроса, которая необходима службе
            // (не требуется, когда is Browser Executable имеет значение true)
            if (presignedGetObjectRequest.signedPayload().isPresent()) {
                connection.setDoOutput(true);
                try (InputStream signedPayload = presignedGetObjectRequest.signedPayload().get().asInputStream();
                     OutputStream httpOutputStream = connection.getOutputStream()) {
                    IoUtils.copy(signedPayload, httpOutputStream);
                }
            }

            // Загрузите результат выполнения запроса
            try (InputStream content = connection.getInputStream()) {
                System.out.println("Service returned response: ");
                IoUtils.copy(content, System.out);
            }

        } catch (S3Exception e) {
            e.getStackTrace();
        } catch (IOException e) {
            e.getStackTrace();
        }
    }
public S3Presigner s3Presigner(){
    return S3Presigner.builder()
            .credentialsProvider(credentialsProvider)
            .region(Region.EU_CENTRAL_1).build();
}
//
//    S3Presigner presigner = S3Presigner.builder()
//            .serviceConfiguration(S3Configuration.builder()
//                    .checksumValidationEnabled(false)
//                    .build())
//            .build();
}
