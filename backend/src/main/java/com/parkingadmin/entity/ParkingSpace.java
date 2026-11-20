package com.parkingadmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {
    // El id es String a propósito: es el código que el personal ve y usa en las rutas (A-01, B-03),
    // no una clave sustituta. Por eso no se usa bigint como en Activity y Revenue.
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

    // Instante de ingreso; null si la plaza no fue ocupada por /api/entries (p. ej. datos de prueba o edición manual).
    @Column(name = "entered_at")
    private Instant enteredAt;

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

    // Edición manual: no se conoce el instante de ingreso, así que se limpia.
    // PENDIENTE DE DECIDIR: si la edición no cambia el vehículo, convendría conservar entered_at
    // para no perder la estadía. Hoy no se hace.
    public void update(String zone, SpaceStatus status, String vehicle, String since) {
        this.zone = zone;
        this.status = status;
        this.vehicle = vehicle;
        this.since = since;
        this.enteredAt = null;
    }

    public void enter(String vehicle, String since, Instant enteredAt) {
        this.status = SpaceStatus.OCCUPIED;
        this.vehicle = vehicle;
        this.since = since;
        this.enteredAt = enteredAt;
    }

    public void release() {
        this.status = SpaceStatus.AVAILABLE;
        this.vehicle = null;
        this.since = null;
        this.enteredAt = null;
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

    public Instant getEnteredAt() {
        return enteredAt;
    }
}
