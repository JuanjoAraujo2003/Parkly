package com.parkingadmin.controller;

import com.parkingadmin.dto.Dashboard;
import com.parkingadmin.dto.Tariff;
import com.parkingadmin.service.DashboardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api")
public class ParkingController {
    private final List<Tariff> tariffs = new CopyOnWriteArrayList<>(List.of(
            new Tariff("Hora estándar", new BigDecimal("4500"), "por hora", true),
            new Tariff("Fracción", new BigDecimal("2500"), "hasta 30 min", true),
            new Tariff("Tarifa nocturna", new BigDecimal("18000"), "de 20:00 a 06:00", true)
    ));

    private final DashboardService dashboard;

    public ParkingController(DashboardService dashboard) {
        this.dashboard = dashboard;
    }

    @GetMapping("/dashboard")
    public Dashboard dashboard() {
        return dashboard.load();
    }

    @GetMapping("/tariffs")
    public List<Tariff> tariffs() {
        return tariffs;
    }

    @PostMapping("/tariffs")
    @ResponseStatus(HttpStatus.CREATED)
    public Tariff createTariff(@Valid @RequestBody Tariff tariff) {
        tariffs.add(tariff);
        return tariff;
    }
}
