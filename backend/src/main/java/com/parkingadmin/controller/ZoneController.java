package com.parkingadmin.controller;

import com.parkingadmin.service.ParkingSpaceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/zones")
public class ZoneController {
    private final ParkingSpaceService spaces;

    public ZoneController(ParkingSpaceService spaces) {
        this.spaces = spaces;
    }

    // Códigos de zona existentes en las plazas, ordenados alfabéticamente.
    @GetMapping
    public List<String> list() {
        return spaces.zones();
    }
}
