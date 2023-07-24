package com.example.blog.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    void saveImage(MultipartFile file) throws IOException;
    Resource loadImageAsResource(String fileName);
}
