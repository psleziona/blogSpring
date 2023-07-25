package com.example.blog.controller;

import com.example.blog.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/images")
public class ImageController {
    private ImageService imageService;

    @GetMapping("/{fileName}")
    public ResponseEntity<Resource> downloadImage(@PathVariable String fileName) {
        Resource resource = imageService.loadImageAsResource(fileName);

        if (resource != null && resource.exists()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Plik jest pusty");
        }

        try {
            imageService.saveImage(file);
            return ResponseEntity.ok("");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas zapisywania obrazu.");
        }
    }
}
