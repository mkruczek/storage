package com.mkruczek.storage.controller;

import com.mkruczek.storage.model.StorageDto;
import com.mkruczek.storage.model.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dto.getName() + "\"")
                .body(dto.toResource());


//
//        String localStoragePath = appConfig.getLocalStoragePath();
//
//////        Path path = Paths.get("/home/kruczek/Pictures/kwitek.png");
////        Path path = Paths.get("/home/kruczek/Desktop/test");
//        Resource resource = null;
//        try {
//            resource = new UrlResource(path.toAbsolutePath().toUri());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        if (!resource.getFile().exists()) {
//          return ResponseEntity.notFound().build();
//        }
//
//        return ResponseEntity.ok()
////                .contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//                .body(resource);
    }

    @PostMapping("/data")
    public StorageDto saveFile(@RequestParam("file") MultipartFile resource, HttpServletRequest request) throws IOException {
        return service.saveResource(resource);
    }

    @GetMapping("/test")
    public String test() {
        return "200 ok";
    }


}
