package com.parkingadmin.parking;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParkingSpaceNotFoundException extends RuntimeException {
    public ParkingSpaceNotFoundException(String id) {
        super("No existe el cajón " + id);
    }
}
