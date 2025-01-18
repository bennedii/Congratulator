package com.example.Congratulator.controller;

import com.example.Congratulator.entity.BirthdayEntity;
import com.example.Congratulator.service.BirthdayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Controller
@RequestMapping("/birthday")
public class BirthdayPageController {

    private final BirthdayService bService;

    public BirthdayPageController(BirthdayService bService) {
        this.bService = bService;
    }

    @GetMapping
    public String getAllBirthdays(Model model) {
        model.addAttribute("birthdays", bService.getAllBirthdays());
        return "birthdays";
    }

    @GetMapping("/update/{id}")
    public String getUpdatePage(@PathVariable Long id, Model model) {
        BirthdayEntity birthday = bService.getBirthdayById(id);
        model.addAttribute("birthday", birthday);
        return "updateBirthday";
    }

    @PostMapping("/update/{id}")
    public String updateBirthday(@PathVariable Long id,
                                 @RequestParam("name") String name,
                                 @RequestParam("dateOfBirth") LocalDate dateOfBirth,
                                 @RequestParam(value = "file", required = false) MultipartFile file) {
        bService.updateBirthday(id, name, dateOfBirth, file);
        return "redirect:/birthday";
    }

    @PostMapping("/delete/{id}")
    public String deleteBirthday(@PathVariable Long id) {
        bService.deleteBirthday(id);
        return "redirect:/birthday";
    }
}
