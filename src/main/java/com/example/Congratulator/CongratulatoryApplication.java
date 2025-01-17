package com.example.Congratulator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CongratulatoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CongratulatoryApplication.class, args);
	}

}
