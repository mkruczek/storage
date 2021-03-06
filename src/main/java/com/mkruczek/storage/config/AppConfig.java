package com.mkruczek.storage.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties
public class AppConfig {

    @Value("${local.storage.path}")
    private String localStoragePath;

    @Value("${google.bucket.name}")
    private String bucketName;

    @Value("${google.credentials.path}")
    private String googleCredentialsPath;

    @Value("${local.temporary.path}")
    private String tmpLocalPath;

    @Value("${google.project}")
    private String googleProject;
}
