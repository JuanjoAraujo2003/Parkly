package com.parkingadmin.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record Payment(String vehicle, BigDecimal amount, Instant recordedAt) {
}
