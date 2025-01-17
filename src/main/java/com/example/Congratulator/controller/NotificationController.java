package com.example.Congratulator.controller;

import com.example.Congratulator.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/setCurrentChatId")
    public ResponseEntity<String> setCurrentChatId(@RequestParam String chatId) {
        if (!notificationService.isChatIdIsValid(chatId)) {
            return new ResponseEntity<>("You must to set available chat id",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(notificationService.setCurrentChatId(chatId), HttpStatus.OK);
    }

    @PostMapping("/sendMessage")
    public ResponseEntity<Void> sendMessage(@RequestParam String message) {
        notificationService.sendMessage(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/sendImage")
    public ResponseEntity<Void> sendImage(@RequestParam String message, @RequestParam String imageUrl) {
        notificationService.sendPhoto(message,imageUrl);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
