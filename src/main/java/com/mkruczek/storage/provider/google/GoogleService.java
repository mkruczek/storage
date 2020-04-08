package com.mkruczek.storage.provider.google;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.mkruczek.storage.config.AppConfig;
import com.mkruczek.storage.exception.exceptions.FailedDownloadResourceException;
import com.mkruczek.storage.model.StorageDto;
import com.mkruczek.storage.model.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class GoogleService {

    Logger logger = LoggerFactory.getLogger(StorageService.class);

    AppConfig config;

    @Autowired
    public GoogleService(AppConfig config) {
        this.config = config;
    }

    public void uploadResources(MultipartFile file) throws IOException {
        Credentials credentials = GoogleCredentials
                .fromStream(new FileInputStream(config.getGoogleCredentialsPath()));
        Storage googleStorage = StorageOptions.newBuilder().setCredentials(credentials)
                .setProjectId(config.getGoogleProject()).build().getService();

        BlobId blobId = BlobId.of(config.getBucketName(), file.getOriginalFilename());
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        googleStorage.create(blobInfo, file.getBytes());

        logger.info("File uploaded to bucket " + config.getBucketName() + " as " + file.getOriginalFilename());
    }

    public StorageDto downloadResources(StorageDto dto) {
        try {
            Credentials credentials = GoogleCredentials
                    .fromStream(new FileInputStream(config.getGoogleCredentialsPath()));
            Storage googleStorage = StorageOptions.newBuilder().setCredentials(credentials)
                    .setProjectId(config.getGoogleProject()).build().getService();

            Blob blob = googleStorage.get(BlobId.of(config.getBucketName(), dto.getName()));
            String downloadPath = config.getTmpLocalPath() + "/" + dto.getName();
            blob.downloadTo(Paths.get(downloadPath));

            dto.setStoragePath("google::" + downloadPath);
            return dto;
        } catch (IOException ex) {
            throw new FailedDownloadResourceException(ex.getMessage());
        }
    }


}
