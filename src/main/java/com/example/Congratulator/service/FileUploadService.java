package com.example.Congratulator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
public class FileUploadService {

    @Value(value = "${upload.path}")
    private String path;

    public String uploadFile(MultipartFile file) {
        try {
            // Проверка на существование директории, если её нет — создание
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Генерация уникального имени для файла
            String fileName = UUID.randomUUID().toString() + '-' + file.getOriginalFilename();
            Path path = Paths.get(this.path + File.separator + fileName);

            // Запись файла в указанную папку
            Files.write(path, file.getBytes());

            // Возвращаем путь к сохраненному файлу
            return fileName;

        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
