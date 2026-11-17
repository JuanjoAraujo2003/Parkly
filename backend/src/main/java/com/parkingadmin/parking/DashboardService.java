package com.parkingadmin.parking;

import com.parkingadmin.entity.Activity;
import com.parkingadmin.entity.ActivityRepository;
import com.parkingadmin.entity.Revenue;
import com.parkingadmin.entity.RevenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

@Service
public class DashboardService {
    private final ParkingSpaceService spaces;
    private final ActivityRepository activities;
    private final RevenueRepository revenues;

    public DashboardService(ParkingSpaceService spaces, ActivityRepository activities, RevenueRepository revenues) {
        this.spaces = spaces;
        this.activities = activities;
        this.revenues = revenues;
    }

    @Transactional(readOnly = true)
    public Dashboard load() {
        List<ParkingSpace> spaceList = spaces.findAll();
        long total = spaceList.size();
        long occupied = count(spaceList, SpaceStatus.OCCUPIED);
        long reserved = count(spaceList, SpaceStatus.RESERVED);
        long available = count(spaceList, SpaceStatus.AVAILABLE);
        long maintenance = count(spaceList, SpaceStatus.MAINTENANCE);
        long occupancy = total == 0 ? 0 : Math.round((occupied + reserved) * 100.0 / total);

        BigDecimal revenue = revenues.findAllByRecordedOn(LocalDate.now()).stream()
                .map(Revenue::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        List<Dashboard.ActivityItem> activity = activities.findTop4ByOrderByOccurredAtDesc().stream()
                .map(DashboardService::toActivityItem)
                .toList();

        return new Dashboard(
                spaceList,
                new Dashboard.Summary(total, occupied, reserved, available, maintenance, occupancy, revenue, occupied),
                activity
        );
    }

    private static long count(List<ParkingSpace> spaceList, SpaceStatus status) {
        return spaceList.stream().filter(space -> space.getStatus() == status).count();
    }

    private static Dashboard.ActivityItem toActivityItem(Activity activity) {
        return new Dashboard.ActivityItem(
                activity.getVehicle(),
                activity.getAction(),
                activity.getType().name().toLowerCase(Locale.ROOT),
                activity.getOccurredAt()
        );
    }
}
