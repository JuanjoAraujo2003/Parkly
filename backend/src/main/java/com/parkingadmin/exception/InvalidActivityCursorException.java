package com.parkingadmin.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidActivityCursorException extends RuntimeException {
    public InvalidActivityCursorException(Long cursor) {
        super("El cursor " + cursor + " no corresponde a ningún registro de actividad");
    }
}
