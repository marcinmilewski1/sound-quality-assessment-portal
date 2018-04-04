package com.sqap.api.domain.file;


import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FileMapKey {
    private final LocalDateTime creationDate;
    private final UUID uuid;
    private final String filename;
    private final String username;

    public FileMapKey(String username, String filename) {
        creationDate = LocalDateTime.now();
        this.uuid = UUID.randomUUID();
        this.username = username;
        this.filename = filename;
    }
}
