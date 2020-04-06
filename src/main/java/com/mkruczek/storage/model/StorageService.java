package com.mkruczek.storage.model;

import com.mkruczek.storage.config.AppConfig;
import com.mkruczek.storage.exception.exceptions.FailedSaveResourceException;
import com.mkruczek.storage.exception.exceptions.ResourceNotFoundException;
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

    @Autowired
    public StorageService(AppConfig config, StorageRepository repository, LocalService localService) {
        this.config = config;
        this.repository = repository;
        this.localService = localService;
    }

    @Transactional
    public StorageDto saveResource(MultipartFile file) {
        String filePath = config.getLocalStoragePath() + "/" + file.getOriginalFilename();
        try {
            StorageEntity save = repository.save(StorageEntity.builder()
                    .id(UUID.randomUUID())
                    .name(file.getOriginalFilename())
                    .path(filePath)
                    .contentType(file.getContentType())
                    .storageDate(LocalDateTime.now())
                    .build());

            localService.saveResource(filePath, file);

            return StorageDto.fromEntity(save);

        } catch (IOException ex) {
            logger.error("Failed to save for : " + filePath, ex);
            throw new FailedSaveResourceException("Failed to save for : " + filePath);
        }
    }

    public List<StorageDto> getResources() {
        return repository.findAll().stream().map(StorageDto::fromEntity).collect(Collectors.toList());
    }

    public StorageDto getResource(UUID id) {
      return repository.findById(id).map(StorageDto::fromEntity).orElseThrow(() -> new ResourceNotFoundException("Not found resource for id: "+ id));
    }
}
