package com.parkingadmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {
    @Id
    @Column(length = 20)
    private String id;

    @Column(nullable = false, length = 10)
    private String zone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SpaceStatus status;

    @Column(length = 10)
    private String vehicle;

    // Hora de ingreso o reserva en formato HH:mm, igual que en el dashboard.
    @Column(name = "since", length = 5)
    private String since;

    // Requerido por JPA.
    protected ParkingSpace() {
    }

    public ParkingSpace(String id, String zone, SpaceStatus status, String vehicle, String since) {
        this.id = id;
        this.zone = zone;
        this.status = status;
        this.vehicle = vehicle;
        this.since = since;
    }

    public void update(String zone, SpaceStatus status, String vehicle, String since) {
        this.zone = zone;
        this.status = status;
        this.vehicle = vehicle;
        this.since = since;
    }

    public String getId() {
        return id;
    }

    public String getZone() {
        return zone;
    }

    public SpaceStatus getStatus() {
        return status;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getSince() {
        return since;
    }
}
