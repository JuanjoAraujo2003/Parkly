package com.parkingadmin.parking;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/spaces")
public class ParkingSpaceController {
    private final ParkingSpaceService spaces;

    public ParkingSpaceController(ParkingSpaceService spaces) {
        this.spaces = spaces;
    }

    @GetMapping
    public List<ParkingSpace> list() {
        return spaces.findAll();
    }

    @GetMapping("/{id}")
    public ParkingSpace get(@PathVariable String id) {
        return spaces.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParkingSpace create(@Valid @RequestBody CreateParkingSpace request) {
        return spaces.create(request.id(), request.zone(), request.status(), request.vehicle(), request.since());
    }

    @PutMapping("/{id}")
    public ParkingSpace update(@PathVariable String id, @Valid @RequestBody UpdateParkingSpace request) {
        return spaces.update(id, request.zone(), request.status(), request.vehicle(), request.since());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        spaces.delete(id);
    }
}
