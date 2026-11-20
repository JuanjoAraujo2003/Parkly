package com.parkingadmin.service;

import com.parkingadmin.dto.DailyReport;
import com.parkingadmin.entity.ActivityType;
import com.parkingadmin.entity.Revenue;
import com.parkingadmin.repository.ActivityRepository;
import com.parkingadmin.repository.RevenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Service
public class ReportService {
    private final ActivityRepository activities;
    private final RevenueRepository revenues;

    public ReportService(ActivityRepository activities, RevenueRepository revenues) {
        this.activities = activities;
        this.revenues = revenues;
    }

    // Usa el día local del servidor, igual que DashboardService.
    @Transactional(readOnly = true)
    public DailyReport daily() {
        LocalDate today = LocalDate.now();
        Instant from = today.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant to = today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();
        BigDecimal revenue = revenues.findAllByRecordedOn(today).stream()
                .map(Revenue::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new DailyReport(
                today,
                revenue,
                activities.countByTypeBetween(ActivityType.ENTRY, from, to),
                activities.countByTypeBetween(ActivityType.EXIT, from, to),
                activities.countByTypeBetween(ActivityType.PAYMENT, from, to)
        );
    }
}
