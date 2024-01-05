package com.example.authservice.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import javax.annotation.PostConstruct;
import java.net.URI;
import java.time.Duration;

@Service
public class StorageService {
    @Value("${STORAGE_ACCESS_KEY}")
    private String STORAGE_ACCESS_KEY;

    @Value("${STORAGE_ACCESS_KEY_ID}")
    private String STORAGE_ACCESS_KEY_ID;

    @Value("${STORAGE_ZONE}")
    private String STORAGE_ZONE;

    @Value("${digitalocean.spaces.endpoint}")
    private String spacesEndpoint;



    private S3Presigner s3Presigner;

    @PostConstruct
    public void init() {
        s3Presigner = S3Presigner.builder()
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(STORAGE_ACCESS_KEY_ID, STORAGE_ACCESS_KEY)))
                .endpointOverride(URI.create(spacesEndpoint))
                .region(Region.of(STORAGE_ZONE))
                .build();
    }

    public String generatePresignedUrl(String bucketName, String objectKey) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .build();

        PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(z -> z
                .signatureDuration(Duration.ofMinutes(15)) // Set the duration for which the URL is valid
                .putObjectRequest(putObjectRequest)
                .build());

        return presignedRequest.url().toString();
    }
}
