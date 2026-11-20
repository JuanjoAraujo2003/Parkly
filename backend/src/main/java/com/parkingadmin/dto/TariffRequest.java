package com.parkingadmin.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

// Cuerpo de POST y PUT de /api/tariffs.
public record TariffRequest(
        @NotBlank String name,
        @NotNull @DecimalMin("0.0") BigDecimal value,
        @NotBlank String unit,
        boolean active
) {
}
