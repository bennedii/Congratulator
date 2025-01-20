package com.example.Congratulator.service;

import com.example.Congratulator.entity.BirthdayEntity;
import com.example.Congratulator.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Value("${telegram.chatId}")
    private String currentChatId;

    private final TelegramBot bot;
    private final UserRepository userRepository;
    private final BirthdayService birthdayService;

    public NotificationService(TelegramBot bot, UserRepository userRepository, BirthdayService birthdayService) {
        this.bot = bot;
        this.userRepository = userRepository;
        this.birthdayService = birthdayService;
    }
    public boolean existByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean isChatIdIsValid(String chatId) {
        return userRepository.existsById(Long.parseLong(chatId));
    }
    public String setCurrentChatId(String chatId) {
        currentChatId = chatId;
        return currentChatId;
    }
    public String setCurrentChatByUsername(String username) {
        return this.currentChatId = userRepository.findByUsername(username).get()
                .getChatId();
    }

    public void sendMessage(String message) {
        bot.sendMessage(currentChatId, message);
    }

    public void sendPhoto(String imageText, String photoUrl) {
        bot.sendImage(currentChatId, imageText, photoUrl);
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void autoNotifications(){
        List<BirthdayEntity> todayBirthdays = birthdayService.getTodayBirthdays();
        List<BirthdayEntity> upcomingBirthdays = birthdayService.getBirthdaysInOneWeek();

        if (!todayBirthdays.isEmpty()){
            bot.sendMessage(currentChatId, "Сегодня день рождение у: ");
            todayBirthdays.forEach(birthdayEntity ->
                    bot.sendImage(currentChatId, birthdayEntity.getName(), birthdayEntity.getPhotoPath())
                    );
        }

        if (!upcomingBirthdays.isEmpty()){
            bot.sendMessage(currentChatId, "В ближайшую неделю дни рождения у: ");
            upcomingBirthdays.forEach( birthdayEntity ->
                    bot.sendImage(currentChatId, birthdayEntity.getName(), birthdayEntity.getPhotoPath()));
        }
    }
}
