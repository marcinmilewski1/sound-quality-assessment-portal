package com.sqap.api.domain.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path rootLocation;

    @Autowired
    public FileStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public UUID addFile(String username, MultipartFile file) {
        if (file.isEmpty()) {
            throw new StorageException("File is empty " + file.getOriginalFilename());
        }
        try {
            UUID uuid = UUID.randomUUID();
            try {
                Files.createDirectory(rootLocation.resolve(uuid.toString()));
            } catch (IOException e) {
                throw new StorageException("Could not create directory", e);
            }
            Path relativePath = Paths.get(uuid.toString(), file.getOriginalFilename());
            Path filePath = this.rootLocation.resolve(relativePath);
            Files.copy(file.getInputStream(), filePath);
            return uuid;
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Path get(UUID uuid) {
        Supplier<Stream<Path>> fileStreamSupplier = () -> {
            try {
                return Files.walk(this.rootLocation.resolve(uuid.toString()));
            } catch (IOException e) {
                throw new UnsupportedOperationException(e);
            }
        };

        try {
            if (fileStreamSupplier.get().count() != 2) throw new StorageException("File system incoherent"); // dir and actual file
            else return fileStreamSupplier.get().filter(path -> path.toFile().isDirectory() == false)
                    .findFirst().orElseThrow(StorageException::new);
        } catch (RuntimeException e) {
            throw new StorageFileNotFoundException("Cannot read file " + uuid);
        }
    }

    @Override
    public Resource getAsResource(UUID uuid) {
        Path filePath = get(uuid);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Cannot read file " + uuid);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Cannot read file " + uuid);
        }
    }

    @Override
    public byte[] getAsBytes(UUID uuid) {
        try {
            return Files.readAllBytes(get(uuid));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Cannot read file from storage");
        }
    }

    @Override
    public void remove(UUID uuid, String username) {
        Path filePath = get(uuid);
        Path parentPath = filePath.getParent();
        if (!parentPath.endsWith(username)) throw new StorageException("Cannot remove file, username incorrent");
        else FileSystemUtils.deleteRecursively(filePath.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }

    }

    @Override
    public void removeAll() {
                FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

}
