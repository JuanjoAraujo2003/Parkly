package com.parkingadmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

// Ingresos registrados por día.
@Entity
@Table(name = "revenue")
public class Revenue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recorded_on", nullable = false)
    private LocalDate recordedOn;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    // Requerido por JPA.
    protected Revenue() {
    }

    public Revenue(LocalDate recordedOn, BigDecimal amount) {
        this.recordedOn = recordedOn;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getRecordedOn() {
        return recordedOn;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
