package com.parkingadmin.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailyReport(LocalDate date, BigDecimal revenue, long entries, long exits, long payments) {
}
