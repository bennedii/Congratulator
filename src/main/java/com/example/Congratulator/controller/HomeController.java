package com.example.Congratulator.controller;

import com.example.Congratulator.entity.BirthdayEntity;
import com.example.Congratulator.service.BirthdayService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final BirthdayService bService;

    public HomeController(BirthdayService birthdayService) {
        this.bService = birthdayService;
    }

    @GetMapping("/home")
    public String getUpcomingBirthdays(Model model) {
        List<BirthdayEntity> upcomingBirthdays = bService.getUpcomingBirthdays();
        model.addAttribute("birthdays", upcomingBirthdays);
        return "home";
    }
}
