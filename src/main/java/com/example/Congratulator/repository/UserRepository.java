package com.example.Congratulator.repository;

import com.example.Congratulator.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<TelegramUser, Long> {
    Optional<TelegramUser> findByUsername(String username);
    boolean existsByUsername(String username);
}
