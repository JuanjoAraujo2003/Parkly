package com.parkingadmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

// Ocupación (plazas ocupadas o reservadas sobre el total) vigente desde recordedAt.
@Entity
@Table(name = "occupancy_snapshot")
public class OccupancySnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recorded_at", nullable = false)
    private Instant recordedAt;

    @Column(name = "in_use", nullable = false)
    private int inUse;

    @Column(nullable = false)
    private int total;

    // Requerido por JPA.
    protected OccupancySnapshot() {
    }

    public OccupancySnapshot(Instant recordedAt, int inUse, int total) {
        this.recordedAt = recordedAt;
        this.inUse = inUse;
        this.total = total;
    }

    public Instant getRecordedAt() {
        return recordedAt;
    }

    public int getInUse() {
        return inUse;
    }

    public int getTotal() {
        return total;
    }
}
