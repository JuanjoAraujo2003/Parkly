package com.parkingadmin.controller;

import com.parkingadmin.dto.ActivityPage;
import com.parkingadmin.service.ActivityService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
    private final ActivityService activity;

    public ActivityController(ActivityService activity) {
        this.activity = activity;
    }

    // Historial del más reciente al más antiguo. Para la página siguiente, enviar nextCursor como cursor.
    @GetMapping
    public ActivityPage history(
            @RequestParam(required = false) Long cursor,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int limit
    ) {
        return activity.history(cursor, limit);
    }
}
