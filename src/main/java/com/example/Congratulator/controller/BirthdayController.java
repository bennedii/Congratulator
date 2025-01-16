package com.example.Congratulator.controller;

import com.example.Congratulator.entity.BirthdayEntity;
import com.example.Congratulator.service.BirthdayService;
import com.example.Congratulator.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/birthday")
public class BirthdayController {

    private final BirthdayService bService;

    public BirthdayController(BirthdayService bService) {
        this.bService = bService;
    }

    @PostMapping("/addBirthday")
    public ResponseEntity<BirthdayEntity> addBirthday(@RequestParam("file") MultipartFile file,
                                                      @RequestParam LocalDate birthday,
                                                      @RequestParam String name) {
        return new ResponseEntity<>(bService.addBirthday(file,birthday,name), HttpStatus.CREATED);
    }

}
