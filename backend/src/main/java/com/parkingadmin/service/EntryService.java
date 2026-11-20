package com.parkingadmin.service;

import com.parkingadmin.entity.Activity;
import com.parkingadmin.entity.ActivityType;
import com.parkingadmin.entity.ParkingSpace;
import com.parkingadmin.entity.SpaceStatus;
import com.parkingadmin.exception.NoAvailableSpaceException;
import com.parkingadmin.exception.VehicleAlreadyParkedException;
import com.parkingadmin.repository.ActivityRepository;
import com.parkingadmin.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
public class EntryService {
    private static final DateTimeFormatter HOUR_MINUTE = DateTimeFormatter.ofPattern("HH:mm");

    private final ParkingSpaceRepository spaces;
    private final ActivityRepository activities;
    private final OccupancySnapshotService snapshots;

    public EntryService(ParkingSpaceRepository spaces, ActivityRepository activities, OccupancySnapshotService snapshots) {
        this.spaces = spaces;
        this.activities = activities;
        this.snapshots = snapshots;
    }

    // Asigna la primera plaza disponible (por id) al vehículo y registra el ingreso.
    @Transactional
    public ParkingSpace register(String vehicle) {
        if (spaces.existsByVehicleAndStatus(vehicle, SpaceStatus.OCCUPIED)) {
            throw new VehicleAlreadyParkedException(vehicle);
        }
        ParkingSpace space = spaces.findFirstByStatusOrderByIdAsc(SpaceStatus.AVAILABLE)
                .orElseThrow(NoAvailableSpaceException::new);
        Instant now = Instant.now();
        space.enter(vehicle, HOUR_MINUTE.format(LocalTime.now()), now);
        activities.save(new Activity(vehicle, "Ingreso a plaza " + space.getId(), ActivityType.ENTRY, now));
        snapshots.record();
        return space;
    }
}
