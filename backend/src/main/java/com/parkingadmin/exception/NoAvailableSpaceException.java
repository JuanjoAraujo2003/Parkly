package com.parkingadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class NoAvailableSpaceException extends RuntimeException {
    public NoAvailableSpaceException() {
        super("No hay plazas disponibles");
    }
}
