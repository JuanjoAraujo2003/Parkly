package com.parkingadmin.controller;

import com.parkingadmin.dto.CreateExit;
import com.parkingadmin.entity.ParkingSpace;
import com.parkingadmin.service.ExitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/exits")
public class ExitController {
    private final ExitService exits;

    public ExitController(ExitService exits) {
        this.exits = exits;
    }

    // Devuelve la plaza ya liberada.
    @PostMapping
    public ParkingSpace register(@Valid @RequestBody CreateExit request) {
        return exits.register(request.vehicle());
    }
}
