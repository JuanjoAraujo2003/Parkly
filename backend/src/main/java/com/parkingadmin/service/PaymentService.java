package com.parkingadmin.service;

import com.parkingadmin.dto.Payment;
import com.parkingadmin.entity.Activity;
import com.parkingadmin.entity.ActivityType;
import com.parkingadmin.entity.Revenue;
import com.parkingadmin.repository.ActivityRepository;
import com.parkingadmin.repository.RevenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Service
public class PaymentService {
    private final RevenueRepository revenues;
    private final ActivityRepository activities;

    public PaymentService(RevenueRepository revenues, ActivityRepository activities) {
        this.revenues = revenues;
        this.activities = activities;
    }

    // Suma el pago a los ingresos del día y lo registra en la actividad. No libera la plaza.
    @Transactional
    public Payment register(String vehicle, BigDecimal amount) {
        Instant now = Instant.now();
        revenues.save(new Revenue(LocalDate.now(), amount));
        activities.save(new Activity(vehicle, "Pago registrado", ActivityType.PAYMENT, now));
        return new Payment(vehicle, amount, now);
    }
}
