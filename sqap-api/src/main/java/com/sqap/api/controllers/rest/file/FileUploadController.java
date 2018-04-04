package com.sqap.api.controllers.rest.file;

import com.sqap.api.base.AuthenticationFacade;
import com.sqap.api.domain.file.FileStorageService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/files")
@PreAuthorize(value = "hasRole('ROLE_USER')")
public class FileUploadController {

    private FileStorageService fileStorageService;
    private AuthenticationFacade authenticationFacade;

    @Autowired
    public FileUploadController(FileStorageService fileUploadService, AuthenticationFacade authenticationFacade) {
        this.fileStorageService = fileUploadService;
        this.authenticationFacade = authenticationFacade;
    }

    @PostMapping
    public ResponseEntity<UUIDDto> uploadFile(@RequestParam("file") MultipartFile file) {
        UUID uuid = this.fileStorageService.addFile(authenticationFacade.getAuthentication().getName(), file);
        if (uuid != null) return ResponseEntity.ok(new UUIDDto(uuid));
        else return new ResponseEntity<UUIDDto>(HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    class UUIDDto {
        private UUID uuid;
    }
}