package com.parkingadmin.dto;

import com.parkingadmin.entity.SpaceStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateParkingSpace(
        @NotBlank @Size(max = 20) String id,
        @NotBlank @Size(max = 10) String zone,
        @NotNull SpaceStatus status,
        @Size(max = 10) String vehicle,
        @Pattern(regexp = "\\d{2}:\\d{2}") String since
) {
}
