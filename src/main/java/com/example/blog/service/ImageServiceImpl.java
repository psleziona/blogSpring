package com.example.blog.service;

import jakarta.servlet.ServletContext;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService{
    private final ServletContext servletContext;
    @Override
    public void saveImage(MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Path path = Paths.get(servletContext.getAttribute("uploadDirectory") + "/" + file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, bytes);
    }

    @Override
    public Resource loadImageAsResource(String fileName) {
        try {
            Path filePath = Paths.get((String) servletContext.getAttribute("uploadDirectory")).resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
