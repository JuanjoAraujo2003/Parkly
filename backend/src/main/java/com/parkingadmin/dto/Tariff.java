package com.parkingadmin.dto;

import java.math.BigDecimal;

// id es numérico y lo asigna el servidor.
public record Tariff(
        Long id,
        String name,
        BigDecimal value,
        String unit,
        boolean active
) {
}
