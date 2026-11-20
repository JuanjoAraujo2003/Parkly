package com.parkingadmin.service;

import com.parkingadmin.entity.OccupancySnapshot;
import com.parkingadmin.entity.ParkingSpace;
import com.parkingadmin.entity.SpaceStatus;
import com.parkingadmin.repository.OccupancySnapshotRepository;
import com.parkingadmin.repository.ParkingSpaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

// Se llama después de cada cambio de estado de una plaza, para poder comparar la ocupación con ayer.
@Service
public class OccupancySnapshotService {
    private final ParkingSpaceRepository spaces;
    private final OccupancySnapshotRepository snapshots;

    public OccupancySnapshotService(ParkingSpaceRepository spaces, OccupancySnapshotRepository snapshots) {
        this.spaces = spaces;
        this.snapshots = snapshots;
    }

    // Las consultas de findAll hacen flush de los cambios pendientes, así que el conteo ya incluye la modificación en curso.
    @Transactional
    public void record() {
        List<ParkingSpace> all = spaces.findAll();
        int inUse = (int) all.stream()
                .filter(space -> space.getStatus() == SpaceStatus.OCCUPIED || space.getStatus() == SpaceStatus.RESERVED)
                .count();
        snapshots.save(new OccupancySnapshot(Instant.now(), inUse, all.size()));
    }

    @Transactional(readOnly = true)
    public Optional<OccupancySnapshot> at(Instant instant) {
        return snapshots.findFirstByRecordedAtLessThanEqualOrderByRecordedAtDesc(instant);
    }
}
