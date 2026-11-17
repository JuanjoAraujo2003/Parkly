package com.parkingadmin.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RevenueRepository extends JpaRepository<Revenue, Long> {
    List<Revenue> findAllByRecordedOn(LocalDate recordedOn);
}
