package com.mkruczek.storage.model;

import com.mkruczek.storage.exception.exceptions.ProviderNotFoundException;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@ToString
public class StorageDto {

    public UUID id;
    public String name;
    public String storagePath;
    public String contentType;
    public LocalDateTime storageDate;

    public static StorageDto fromEntity(StorageEntity entity) {
        return StorageDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .storagePath(entity.getPath())
                .contentType(entity.getContentType())
                .storageDate(entity.getStorageDate())
                .build();
    }

    public Resource toResource() {
        return new FileSystemResource(this.valueOfPath());
    }

    public String valueOfPath() {
        return this.storagePath.split("::")[1];
    }

    public String getProvider() {
        return this.storagePath.split("::")[0];
    }

    public static String createStoragePath(String provider, String path, String name) {
        switch (provider) {
            case "local":
                return provider + "::" + path + "/" + name;
            case "google":
                return provider + "::" + name;
            default:
                throw new ProviderNotFoundException(provider);
        }
    }
}
