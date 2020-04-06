package com.mkruczek.storage.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity(name = "storage")
public class StorageEntity {

    public StorageEntity() {
    }

    public StorageEntity(UUID id, String name, String path, String contentType, LocalDateTime storageDate) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.contentType = contentType;
        this.storageDate = storageDate;
    }

    @Id
    @Column(name = "id")
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "path")
    private String path;
    @Column(name = "content_type")
    private String contentType;
    @Column(name = "storage_date")
    private LocalDateTime storageDate;
}
