package com.example.Congratulator.service;

import com.example.Congratulator.entity.TelegramUser;
import com.example.Congratulator.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.nio.file.Path;
import java.util.Optional;

@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.username}")
    private String botUsername;

    public TelegramBot(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            User user = update.getMessage().getFrom();
            String chatId = update.getMessage().getChatId().toString();

            if (userRepository.existsByUsername(user.getUserName())) {
                TelegramUser newUser = TelegramUser.builder()
                        .username(user.getUserName())
                        .lastName(user.getLastName())
                        .firstName(user.getFirstName())
                        .id(user.getId())
                        .languageCode(user.getLanguageCode())
                        .build();
                userRepository.save(newUser);
                log.info("User with username {} was successfully registered", user.getFirstName());
            }
            log.info("Received message: {} from chat: {}", userMessage, chatId);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(user.getId().toString());
            sendMessage.setText("You are welcome!");

            try {
                execute(sendMessage); // Отправка сообщения
                log.info("Message sent successfully to chat: {}", chatId);
            } catch (Exception e) {
                log.error("Error while sending message: ", e);
            }
        }
    }

    public void sendMessage(String chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (Exception e) {
            log.error("Error while sending message: ", e);
        }
    }
    public void sendImage(String chatId, String imageText, String imageUrl) {
        try{
        ClassPathResource resource = new ClassPathResource("upload/images/" + imageUrl);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(resource.getFile()));
        sendPhoto.setCaption(imageText);
            execute(sendPhoto);
        } catch (Exception exception){
            log.error("Error while sending message: ", exception);
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}

