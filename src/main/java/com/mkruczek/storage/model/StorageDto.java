package com.mkruczek.storage.model;

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
    public String path;
    public String contentType;
    public LocalDateTime storageDate;

    public static StorageDto fromEntity(StorageEntity entity) {
        return StorageDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .path(entity.getPath())
                .contentType(entity.getContentType())
                .storageDate(entity.getStorageDate())
                .build();
    }

    public Resource toResource(){
        return new FileSystemResource(this.path);
    }


//    public static void main(String[] args) {
//
//        StorageDto heniek = StorageDto.builder()
//                .id(UUID.randomUUID())
//                .name("heniek")
//                .build();
//
//        doMagic(heniek);
//
//
//        System.out.println(heniek);
//
//
//
//    }
//
//    private static void doMagic(StorageDto dto){
//        dto.setName("kurwa mac");
//    }
}
