package com.demo.fileupload.storage;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.demo.fileupload.common.exception.UpstreamDependencyException;
import com.demo.fileupload.document.domain.Document;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Optional;

@Slf4j
@Component
public class S3StorageProvider implements StorageProvider<Document> {

    @Value("${file.storage.s3.temporary.bucket.name}")
    private String temporaryBucketName;

    @Value("${aws.s3.region}")
    private String region;

    @Value("${file.storage.s3.token.expiration}")
    private long tokenExpiration;

    AmazonS3 s3Client;

    MessageDigest messageDigest;

    @SneakyThrows
    @PostConstruct
    void init() throws NoSuchAlgorithmException {
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new ProfileCredentialsProvider())
                .withRegion(region)
                .build();

        messageDigest = MessageDigest.getInstance("MD5");
    }

    @Override
    public String getUploadFileUrl(Document document) {
        return generatePreSignedUrl(tokenExpiration, document, HttpMethod.POST).map(url ->
            url.toString()
        ).orElseThrow(() -> new UpstreamDependencyException("Failed to get Upload file url from S3"));
    }

    @Override
    public String getFileUrl(Document document) {
        return generatePreSignedUrl(tokenExpiration, document, HttpMethod.GET).map(url ->
                url.toString()
        ).orElseThrow(() -> new UpstreamDependencyException("Failed to get file url from S3"));
    }

    @Override
    public void remove(Document instance) {
        // Remove File from S3 bucket.
    }

    private String getUniqueFileString(Document document) throws UnsupportedEncodingException {
        String uniqueFileString = new StringBuilder(document.getTitle()).append("~").append(document.getUser().getId()).toString();
        return Base64.getEncoder().encodeToString(uniqueFileString.getBytes());
    }

    private Optional<URL> generatePreSignedUrl(Long tokenExpiration, Document document, HttpMethod method) {
        Optional<URL> url = Optional.empty();
        try {
            // Set the pre-signed URL to expire after tokenExpiration period.
            java.util.Date expiration = new java.util.Date();
            long expTimeMillis = expiration.getTime();
            expTimeMillis += tokenExpiration;
            expiration.setTime(expTimeMillis);

            String objectKey = getUniqueFileString(document);

            // Generate the pre-signed URL.
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(temporaryBucketName, objectKey)
                    .withMethod(method)
                    .withExpiration(expiration);

            url = Optional.of(s3Client.generatePresignedUrl(generatePresignedUrlRequest));

        } catch (UnsupportedEncodingException e) {
            throw new UpstreamDependencyException("Failed to get unique file name for S3 storage object", e);
        } catch (SdkClientException e) {
            throw new UpstreamDependencyException("Failed to get Upload file url from S3", e);
        }
        return url;
    }
}
