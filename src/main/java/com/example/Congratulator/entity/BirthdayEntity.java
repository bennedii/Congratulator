package com.example.Congratulator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "birthdays")
public class BirthdayEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50)
    private String name;

    private LocalDate dateOfBirth;  // Дата рождения

    private String photoPath;  // Ссылка на фотографию
}
