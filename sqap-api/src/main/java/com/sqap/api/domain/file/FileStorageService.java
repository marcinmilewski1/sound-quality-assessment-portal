package com.sqap.api.domain.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.UUID;

public interface FileStorageService {
    UUID addFile(String username, MultipartFile file);

    Path get(UUID uuid);

    Resource getAsResource(UUID uuid);

    byte[] getAsBytes(UUID uuid);

    void remove(UUID uuid, String username);

    void init();

    void removeAll();
}
