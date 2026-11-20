package com.parkingadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class VehicleAlreadyParkedException extends RuntimeException {
    public VehicleAlreadyParkedException(String vehicle) {
        super("El vehículo " + vehicle + " ya está en una plaza");
    }
}
