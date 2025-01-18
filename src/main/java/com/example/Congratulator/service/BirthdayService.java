package com.example.Congratulator.service;

import com.example.Congratulator.entity.BirthdayEntity;
import com.example.Congratulator.repository.BirthdayRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@Service
public class BirthdayService {

    private final FileUploadService fileUploadService;
    private final BirthdayRepository birthdayRepository;

    public BirthdayService(FileUploadService fileUploadService, BirthdayRepository birthdayRepository) {
        this.fileUploadService = fileUploadService;
        this.birthdayRepository = birthdayRepository;
    }

    @Transactional
    public BirthdayEntity addBirthday(MultipartFile file, LocalDate birthdayDate, String name){
        BirthdayEntity birthdayEntity = new BirthdayEntity();
        birthdayEntity.setName(name);
        birthdayEntity.setDateOfBirth(birthdayDate);
        birthdayEntity.setPhotoPath(fileUploadService.uploadFile(file));

        return birthdayRepository.save(birthdayEntity);
    }

    public List<BirthdayEntity> getUpcomingBirthdays() {
        return birthdayRepository.findByDateOfBirthBetween(LocalDate.now().minusDays(1), LocalDate.now().plusMonths(1));
    }
    public List<BirthdayEntity> getTodayBirthdays() {
        return birthdayRepository.findTodayBirthdays(LocalDate.now());
    }

    public List<BirthdayEntity> getBirthdaysInOneWeek() {
        return birthdayRepository.findByDateOfBirthBetween(LocalDate.now().plusDays(1),LocalDate.now().plusWeeks(1));
    }

    public void deleteBirthday(Long id) {
        birthdayRepository.deleteById(id);
    }

    public BirthdayEntity updateBirthday(Long id, String name, LocalDate dateOfBirth, MultipartFile file) {
        BirthdayEntity birthday = birthdayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Birthday not found"));

        birthday.setName(name);
        birthday.setDateOfBirth(dateOfBirth);

        if (file != null && !file.isEmpty()) {
            String filePath = fileUploadService.uploadFile(file);
            birthday.setPhotoPath(filePath);
        }

        return birthdayRepository.save(birthday);
    }

    public BirthdayEntity getBirthdayById(Long id) {
        return birthdayRepository.findById(id).orElseThrow(() -> new RuntimeException("Birthday not found"));
    }

    public List<BirthdayEntity> getAllBirthdays() {
        return birthdayRepository.findAll();
    }
}
