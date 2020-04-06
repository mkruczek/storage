package com.mkruczek.storage.provider.local;

import com.mkruczek.storage.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Service
public class LocalService {

    AppConfig config;

    @Autowired
    public LocalService(AppConfig config) {
        this.config = config;
    }

    public void saveResource(String storagePath, MultipartFile file) throws IOException {
        file.transferTo(Path.of(storagePath));
    }
}
