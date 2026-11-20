package com.parkingadmin.service;

import com.parkingadmin.dto.ActivityPage;
import com.parkingadmin.dto.Dashboard;
import com.parkingadmin.entity.Activity;
import com.parkingadmin.exception.InvalidActivityCursorException;
import com.parkingadmin.repository.ActivityRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ActivityService {
    private static final Sort HISTORY_ORDER = Sort.by(Sort.Order.desc("occurredAt"), Sort.Order.desc("id"));

    private final ActivityRepository activities;

    public ActivityService(ActivityRepository activities) {
        this.activities = activities;
    }

    // Pide limit + 1 registros para saber si hay una página siguiente sin una consulta de conteo.
    @Transactional(readOnly = true)
    public ActivityPage history(Long cursor, int limit) {
        PageRequest probe = PageRequest.of(0, limit + 1);
        List<Activity> rows = cursor == null
                ? activities.findAll(PageRequest.of(0, limit + 1, HISTORY_ORDER)).getContent()
                : activities.findAfterCursor(anchor(cursor).getOccurredAt(), cursor, probe);

        boolean hasMore = rows.size() > limit;
        List<Activity> page = hasMore ? rows.subList(0, limit) : rows;
        Long nextCursor = hasMore ? page.getLast().getId() : null;
        return new ActivityPage(page.stream().map(Dashboard.ActivityItem::from).toList(), nextCursor);
    }

    private Activity anchor(Long cursor) {
        return activities.findById(cursor).orElseThrow(() -> new InvalidActivityCursorException(cursor));
    }
}
