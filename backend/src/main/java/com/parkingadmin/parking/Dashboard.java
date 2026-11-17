package com.parkingadmin.parking;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record Dashboard(List<ParkingSpace> spaces, Summary summary, List<ActivityItem> activity) {

    public record Summary(
            long total,
            long occupied,
            long reserved,
            long available,
            long maintenance,
            long occupancy,
            BigDecimal revenue,
            long activeVehicles
    ) {
    }

    // type en minúsculas (entry, reservation, payment, exit), como lo espera el dashboard.
    public record ActivityItem(String vehicle, String action, String type, Instant occurredAt) {
    }
}
