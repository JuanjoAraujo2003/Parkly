package com.parkingadmin.controller;

import com.parkingadmin.config.ParklyProperties;
import com.parkingadmin.dto.Dashboard;
import com.parkingadmin.dto.ParkingInfo;
import com.parkingadmin.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ParkingController {
    private final DashboardService dashboard;
    private final ParklyProperties properties;

    public ParkingController(DashboardService dashboard, ParklyProperties properties) {
        this.dashboard = dashboard;
        this.properties = properties;
    }

    @GetMapping("/dashboard")
    public Dashboard dashboard() {
        return dashboard.load();
    }

    // Nombre y sede del parqueadero, desde la configuración (parkly.parking.*).
    @GetMapping("/parking")
    public ParkingInfo parking() {
        return new ParkingInfo(properties.parking().name(), properties.parking().headquarters());
    }
}
