package com.parkingadmin.parking;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParkingSpaceService {
    private final ParkingSpaceRepository spaces;

    public ParkingSpaceService(ParkingSpaceRepository spaces) {
        this.spaces = spaces;
    }

    @Transactional(readOnly = true)
    public List<ParkingSpace> findAll() {
        return spaces.findAll(Sort.by("id"));
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
        return spaces.save(new ParkingSpace(id, zone, status, vehicle, since));
    }

    @Transactional
    public ParkingSpace update(String id, String zone, SpaceStatus status, String vehicle, String since) {
        ParkingSpace space = findById(id);
        space.update(zone, status, vehicle, since);
        return space;
    }

    @Transactional
    public void delete(String id) {
        spaces.delete(findById(id));
    }
}

