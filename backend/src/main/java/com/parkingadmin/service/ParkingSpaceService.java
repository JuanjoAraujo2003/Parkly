package com.parkingadmin.service;

import com.parkingadmin.entity.ParkingSpace;
import com.parkingadmin.entity.SpaceStatus;
import com.parkingadmin.exception.ParkingSpaceAlreadyExistsException;
import com.parkingadmin.exception.ParkingSpaceNotFoundException;
import com.parkingadmin.repository.ParkingSpaceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParkingSpaceService {
    private final ParkingSpaceRepository spaces;
    private final OccupancySnapshotService snapshots;

    public ParkingSpaceService(ParkingSpaceRepository spaces, OccupancySnapshotService snapshots) {
        this.spaces = spaces;
        this.snapshots = snapshots;
    }

    @Transactional(readOnly = true)
    public List<ParkingSpace> findAll() {
        return spaces.findAll(Sort.by("id"));
    }

    @Transactional(readOnly = true)
    public List<String> zones() {
        return spaces.findDistinctZones();
    }

    @Transactional(readOnly = true)
    public ParkingSpace findById(String id) {
        return spaces.findById(id).orElseThrow(() -> new ParkingSpaceNotFoundException(id));
    }

    @Transactional
    public ParkingSpace create(String id, String zone, SpaceStatus status, String vehicle, String since) {
        if (spaces.existsById(id)) {
            throw new ParkingSpaceAlreadyExistsException(id);
        }
        ParkingSpace space = spaces.save(new ParkingSpace(id, zone, status, vehicle, since));
        snapshots.record();
        return space;
    }

    @Transactional
    public ParkingSpace update(String id, String zone, SpaceStatus status, String vehicle, String since) {
        ParkingSpace space = findById(id);
        space.update(zone, status, vehicle, since);
        snapshots.record();
        return space;
    }

    @Transactional
    public void delete(String id) {
        spaces.delete(findById(id));
        snapshots.record();
    }
}

