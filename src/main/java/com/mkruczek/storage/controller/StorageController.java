package com.mkruczek.storage.controller;

import com.mkruczek.storage.model.StorageDto;
import com.mkruczek.storage.model.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("storage/api/v1")
public class StorageController {

    StorageService service;

    @Autowired
    public StorageController(StorageService service) {
        this.service = service;
    }

    @GetMapping("/data")
    public List<StorageDto> getResources() {
        return service.getResources();
    }

    @GetMapping("/data/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getResource(@PathVariable UUID id) {

        StorageDto dto = service.getResource(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dto.getContentType()))
                .body(dto.toResource());
    }

    @PostMapping("/data/{provider}")
    public StorageDto saveFile(@PathVariable String provider, @RequestParam("file") MultipartFile resource) {
        return service.saveResource(provider, resource);
    }
}
