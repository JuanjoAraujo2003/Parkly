package com.parkingadmin.controller;

import com.parkingadmin.dto.CreateEntry;
import com.parkingadmin.entity.ParkingSpace;
import com.parkingadmin.service.EntryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/entries")
public class EntryController {
    private final EntryService entries;

    public EntryController(EntryService entries) {
        this.entries = entries;
    }

    // Devuelve la plaza asignada.
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSpace register(@Valid @RequestBody CreateEntry request) {
        return entries.register(request.vehicle());
    }
}
