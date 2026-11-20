package com.parkingadmin.controller;

import com.parkingadmin.dto.Tariff;
import com.parkingadmin.dto.TariffActivation;
import com.parkingadmin.dto.TariffChange;
import com.parkingadmin.dto.TariffRequest;
import com.parkingadmin.service.TariffService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tariffs")
public class TariffController {
    // Unidades que acepta una tarifa; el frontend las usa para el select del modal.
    private static final List<String> UNITS = List.of("por hora", "hasta 30 min", "tarifa plana", "de 20:00 a 06:00");

    private final TariffService tariffs;

    public TariffController(TariffService tariffs) {
        this.tariffs = tariffs;
    }

    @GetMapping
    public List<Tariff> list() {
        return tariffs.findAll();
    }

    @GetMapping("/units")
    public List<String> units() {
        return UNITS;
    }

    @GetMapping("/history")
    public List<TariffChange> history() {
        return tariffs.history();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tariff create(@Valid @RequestBody TariffRequest request) {
        return tariffs.create(request);
    }

    @PutMapping("/{id}")
    public Tariff update(@PathVariable Long id, @Valid @RequestBody TariffRequest request) {
        return tariffs.update(id, request);
    }

    @PatchMapping("/{id}/active")
    public Tariff setActive(@PathVariable Long id, @RequestBody TariffActivation request) {
        return tariffs.setActive(id, request.active());
    }
}
