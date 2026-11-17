package com.parkingadmin.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record Tariff(
        @NotBlank String name,
        @DecimalMin("0.0") BigDecimal value,
        @NotBlank String unit,
        boolean active
) {
}
