package com.parkingadmin.repository;

import com.parkingadmin.entity.OccupancySnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface OccupancySnapshotRepository extends JpaRepository<OccupancySnapshot, Long> {
    // Ocupación vigente en un instante: el último snapshot anterior o igual a él.
    Optional<OccupancySnapshot> findFirstByRecordedAtLessThanEqualOrderByRecordedAtDesc(Instant at);
}
