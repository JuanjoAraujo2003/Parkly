package com.parkingadmin.dto;

import java.util.List;

// nextCursor es el id del último elemento de la página; null cuando no hay más registros.
public record ActivityPage(List<Dashboard.ActivityItem> items, Long nextCursor) {
}
