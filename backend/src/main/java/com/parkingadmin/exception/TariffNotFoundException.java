package com.parkingadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TariffNotFoundException extends RuntimeException {
    public TariffNotFoundException(Long id) {
        super("No existe la tarifa " + id);
    }
}
