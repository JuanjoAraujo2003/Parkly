package com.parkingadmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

// Registro de actividad reciente del panel (ingresos, reservas, pagos y salidas).
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String vehicle;

    @Column(nullable = false, length = 100)
    private String action;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ActivityType type;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    // Solo en salidas: minutos de estadía, o null si no se conocía el ingreso.
    @Column(name = "stay_minutes")
    private Integer stayMinutes;

    // Requerido por JPA.
    protected Activity() {
    }

    public Activity(String vehicle, String action, ActivityType type, Instant occurredAt) {
        this(vehicle, action, type, occurredAt, null);
    }

    public Activity(String vehicle, String action, ActivityType type, Instant occurredAt, Integer stayMinutes) {
        this.vehicle = vehicle;
        this.action = action;
        this.type = type;
        this.occurredAt = occurredAt;
        this.stayMinutes = stayMinutes;
    }

    public Long getId() {
        return id;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getAction() {
        return action;
    }

    public ActivityType getType() {
        return type;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public Integer getStayMinutes() {
        return stayMinutes;
    }
}
