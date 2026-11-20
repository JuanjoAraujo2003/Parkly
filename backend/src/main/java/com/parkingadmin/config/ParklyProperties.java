package com.parkingadmin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// Datos fijos de la sesión y del parqueadero, configurados en application.properties (prefijo parkly).
@ConfigurationProperties(prefix = "parkly")
public record ParklyProperties(User user, Parking parking) {

    public record User(String name, String role) {
    }

    public record Parking(String name, String headquarters) {
    }
}
