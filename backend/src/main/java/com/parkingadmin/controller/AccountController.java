package com.parkingadmin.controller;

import com.parkingadmin.config.ParklyProperties;
import com.parkingadmin.dto.CurrentUser;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

// Sin autenticación todavía: /api/me devuelve el usuario configurado y el logout no tiene sesión que cerrar.
@RestController
@RequestMapping("/api")
public class AccountController {
    private final ParklyProperties properties;

    public AccountController(ParklyProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/me")
    public CurrentUser me() {
        return CurrentUser.of(properties.user().name(), properties.user().role());
    }

    @PostMapping("/auth/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
    }
}
