package com.example.Congratulator.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "telegram-users")
public class TelegramUser {
    @Id
    private Long id; // Уникальный идентификатор пользователя

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private String languageCode;

    private String chatId;

}