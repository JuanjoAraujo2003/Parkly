package com.parkingadmin.repository;

import com.parkingadmin.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findTop4ByOrderByOccurredAtDesc();
}
