package com.cybersoft.food_project.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    public boolean StoreFile(MultipartFile file);
    Resource loadFileByName(String fileName);
}
