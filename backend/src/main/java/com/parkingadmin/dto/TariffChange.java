package com.parkingadmin.dto;

import java.time.Instant;

// Entrada del historial de cambios de tarifas, del más reciente al más antiguo.
public record TariffChange(Long tariffId, String tariffName, Type type, Instant changedAt) {

    public enum Type {
        CREATED,
        UPDATED,
        ACTIVATED,
        DEACTIVATED
    }
}
