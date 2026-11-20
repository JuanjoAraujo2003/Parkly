package com.parkingadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class VehicleNotParkedException extends RuntimeException {
    public VehicleNotParkedException(String vehicle) {
        super("El vehículo " + vehicle + " no está en una plaza");
    }
}
