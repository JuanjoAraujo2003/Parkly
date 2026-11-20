package com.parkingadmin.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateExit(@NotBlank @Size(max = 10) String vehicle) {
}
