package com.mkruczek.storage.model;

import com.mkruczek.storage.config.AppConfig;
import com.mkruczek.storage.exception.exceptions.FailedSaveResourceException;
import com.mkruczek.storage.exception.exceptions.ProviderNotFoundException;
import com.mkruczek.storage.exception.exceptions.ResourceNotFoundException;
import com.mkruczek.storage.provider.google.GoogleService;
import com.mkruczek.storage.provider.local.LocalService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class StorageService {

    Logger logger = LoggerFactory.getLogger(StorageService.class);

    AppConfig config;
    StorageRepository repository;
    LocalService localService;
    GoogleService googleService;

    @Autowired
    public StorageService(AppConfig config, StorageRepository repository, LocalService localService, GoogleService googleService) {
        this.config = config;
        this.repository = repository;
        this.localService = localService;
        this.googleService = googleService;
    }

    @Transactional
    public StorageDto saveResource(String provider, MultipartFile file) {
        String storagePath = StorageDto.createStoragePath(provider, config.getLocalStoragePath(), file.getOriginalFilename());

        try {
            StorageDto save = StorageDto.fromEntity(
                    repository.save(StorageEntity.builder()
                            .id(UUID.randomUUID())
                            .name(file.getOriginalFilename())
                            .path(storagePath)
                            .contentType(file.getContentType())
                            .storageDate(LocalDateTime.now())
                            .build()));

            switch (provider) {
                case "local":
                    localService.saveResource(save.valueOfPath(), file);
                    break;
                case "google":
                    googleService.uploadResources(file);
                    break;
                default:
                    throw new ProviderNotFoundException(provider);
            }
            return save;

        } catch (IOException ex) {
            logger.error("Failed to save for : " + storagePath, ex);
            throw new FailedSaveResourceException("Failed to save for : " + storagePath);
        }
    }

    public List<StorageDto> getResources() {
        return repository.findAll().stream().map(StorageDto::fromEntity).collect(Collectors.toList());
    }

    public StorageDto getResource(UUID id) {
        StorageDto storageDto = repository.findById(id).map(StorageDto::fromEntity).orElseThrow(() -> new ResourceNotFoundException("Not found resource for id: " + id));

        switch (storageDto.getProvider()) {
            case "local":
                return storageDto;
            case "google":
                return googleService.downloadResources(storageDto);
            default:
                throw new ProviderNotFoundException(storageDto.getProvider());
        }
    }
}
