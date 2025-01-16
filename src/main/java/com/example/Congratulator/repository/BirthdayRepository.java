package com.example.Congratulator.repository;

import com.example.Congratulator.entity.BirthdayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BirthdayRepository extends JpaRepository<BirthdayEntity, Long> {
    @Query(value = "SELECT b FROM BirthdayEntity b where b.dateOfBirth between :start and :end")
    List<BirthdayEntity> findByDateOfBirthBetween(@Param("start") LocalDate startDate,
                                                  @Param("end") LocalDate endDate);
}
