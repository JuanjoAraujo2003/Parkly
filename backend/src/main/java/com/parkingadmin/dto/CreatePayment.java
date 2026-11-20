package com.parkingadmin.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record CreatePayment(
        @NotBlank @Size(max = 10) String vehicle,
        @NotNull @DecimalMin("0.01") BigDecimal amount
) {
}
