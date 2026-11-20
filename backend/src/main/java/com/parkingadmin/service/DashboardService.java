package com.parkingadmin.service;

import com.parkingadmin.dto.Dashboard;
import com.parkingadmin.entity.ActivityType;
import com.parkingadmin.entity.OccupancySnapshot;
import com.parkingadmin.entity.ParkingSpace;
import com.parkingadmin.entity.Revenue;
import com.parkingadmin.entity.SpaceStatus;
import com.parkingadmin.repository.ActivityRepository;
import com.parkingadmin.repository.RevenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {
    private final ParkingSpaceService spaces;
    private final ActivityRepository activities;
    private final RevenueRepository revenues;
    private final OccupancySnapshotService snapshots;

    public DashboardService(ParkingSpaceService spaces, ActivityRepository activities, RevenueRepository revenues,
                            OccupancySnapshotService snapshots) {
        this.spaces = spaces;
        this.activities = activities;
        this.revenues = revenues;
        this.snapshots = snapshots;
    }

    @Transactional(readOnly = true)
    public Dashboard load() {
        Instant now = Instant.now();
        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);

        List<ParkingSpace> spaceList = spaces.findAll();
        long total = spaceList.size();
        long occupied = count(spaceList, SpaceStatus.OCCUPIED);
        long reserved = count(spaceList, SpaceStatus.RESERVED);
        long available = count(spaceList, SpaceStatus.AVAILABLE);
        long maintenance = count(spaceList, SpaceStatus.MAINTENANCE);
        long occupancy = percent(occupied + reserved, total);

        BigDecimal revenue = revenueOn(today);
        BigDecimal yesterdayRevenue = revenueOn(yesterday);

        // Ocupación de hace 24 h: el snapshot vigente en ese instante. Sin snapshot, no hay tendencia.
        Long occupancyTrend = snapshots.at(now.minusSeconds(24 * 60 * 60))
                .map(DashboardService::occupancyPercent)
                .map(previous -> occupancy - previous)
                .orElse(null);

        Double revenueTrend = yesterdayRevenue.signum() == 0 ? null : revenue.subtract(yesterdayRevenue)
                .multiply(BigDecimal.valueOf(100))
                .divide(yesterdayRevenue, 1, RoundingMode.HALF_UP)
                .doubleValue();

        Double averageStay = activities.averageStayBetween(
                ActivityType.EXIT,
                today.atStartOfDay(ZoneId.systemDefault()).toInstant(),
                today.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant()
        );

        List<Dashboard.ActivityItem> activity = activities.findTop4ByOrderByOccurredAtDesc().stream()
                .map(Dashboard.ActivityItem::from)
                .toList();

        return new Dashboard(
                spaceList,
                new Dashboard.Summary(total, occupied, reserved, available, maintenance, occupancy, revenue, occupied,
                        occupancyTrend, revenueTrend, averageStay == null ? null : Math.round(averageStay)),
                activity
        );
    }

    private BigDecimal revenueOn(LocalDate date) {
        return revenues.findAllByRecordedOn(date).stream()
                .map(Revenue::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private static long count(List<ParkingSpace> spaceList, SpaceStatus status) {
        return spaceList.stream().filter(space -> space.getStatus() == status).count();
    }

    private static long percent(long part, long total) {
        return total == 0 ? 0 : Math.round(part * 100.0 / total);
    }

    private static long occupancyPercent(OccupancySnapshot snapshot) {
        return percent(snapshot.getInUse(), snapshot.getTotal());
    }
}
