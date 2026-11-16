package com.parkingadmin.parking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ParkingSpaceAlreadyExistsException extends RuntimeException {
    public ParkingSpaceAlreadyExistsException(String id) {
        super("Ya existe el cajón " + id);
    }
}
