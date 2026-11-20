package com.parkingadmin.dto;

import com.parkingadmin.entity.Activity;
import com.parkingadmin.entity.ParkingSpace;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Locale;

public record Dashboard(List<ParkingSpace> spaces, Summary summary, List<ActivityItem> activity) {

    public record Summary(
            long total,
            long occupied,
            long reserved,
            long available,
            long maintenance,
            long occupancy,
            BigDecimal revenue,
            long activeVehicles,
            // Puntos porcentuales de ocupación frente a hace 24 h; null sin historial.
            Long occupancyTrend,
            // Variación porcentual de ingresos frente a ayer; null si ayer no hubo ingresos.
            Double revenueTrend,
            // Promedio de estadía de las salidas de hoy; null si no hubo salidas con ingreso conocido.
            Long averageStayMinutes
    ) {
    }

    // type en minúsculas (entry, reservation, payment, exit), como lo espera el dashboard.
    // id es el cursor de paginación de /api/activity.
    public record ActivityItem(Long id, String vehicle, String action, String type, Instant occurredAt) {
        public static ActivityItem from(Activity activity) {
            return new ActivityItem(
                    activity.getId(),
                    activity.getVehicle(),
                    activity.getAction(),
                    activity.getType().name().toLowerCase(Locale.ROOT),
                    activity.getOccurredAt()
            );
        }
    }
}
