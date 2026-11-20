package com.parkingadmin.service;

import com.parkingadmin.entity.Activity;
import com.parkingadmin.entity.ActivityType;
import com.parkingadmin.entity.ParkingSpace;
import com.parkingadmin.entity.SpaceStatus;
import com.parkingadmin.exception.VehicleNotParkedException;
import com.parkingadmin.repository.ActivityRepository;
import com.parkingadmin.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
public class ExitService {
    private final ParkingSpaceRepository spaces;
    private final ActivityRepository activities;
    private final OccupancySnapshotService snapshots;

    public ExitService(ParkingSpaceRepository spaces, ActivityRepository activities, OccupancySnapshotService snapshots) {
        this.spaces = spaces;
        this.activities = activities;
        this.snapshots = snapshots;
    }

    // Libera la plaza del vehículo y guarda la estadía. Si el ingreso no se conoce, la estadía queda null.
    @Transactional
    public ParkingSpace register(String vehicle) {
        ParkingSpace space = spaces.findFirstByVehicleAndStatus(vehicle, SpaceStatus.OCCUPIED)
                .orElseThrow(() -> new VehicleNotParkedException(vehicle));
        Instant now = Instant.now();
        Integer stayMinutes = space.getEnteredAt() == null
                ? null
                : (int) Duration.between(space.getEnteredAt(), now).toMinutes();
        String spaceId = space.getId();
        space.release();
        activities.save(new Activity(vehicle, "Salida de plaza " + spaceId, ActivityType.EXIT, now, stayMinutes));
        snapshots.record();
        return space;
    }
}
