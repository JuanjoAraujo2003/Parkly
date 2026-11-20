package com.parkingadmin.service;

import com.parkingadmin.dto.Tariff;
import com.parkingadmin.dto.TariffChange;
import com.parkingadmin.dto.TariffRequest;
import com.parkingadmin.exception.TariffNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Las tarifas y su historial viven en memoria, como el resto de datos de demostración.
// Los métodos son synchronized porque la lista y el historial se modifican juntos.
@Service
public class TariffService {
    private final List<Tariff> tariffs = new ArrayList<>();
    private final List<TariffChange> history = new ArrayList<>();
    private long lastId;

    public TariffService() {
        add("Hora estándar", new BigDecimal("4500"), "por hora", true);
        add("Fracción", new BigDecimal("2500"), "hasta 30 min", true);
        add("Tarifa nocturna", new BigDecimal("18000"), "de 20:00 a 06:00", true);
    }

    public synchronized List<Tariff> findAll() {
        return List.copyOf(tariffs);
    }

    public synchronized Tariff create(TariffRequest request) {
        Tariff tariff = add(request.name(), request.value(), request.unit(), request.active());
        record(tariff, TariffChange.Type.CREATED);
        return tariff;
    }

    public synchronized Tariff update(Long id, TariffRequest request) {
        int index = indexOf(id);
        Tariff tariff = new Tariff(id, request.name(), request.value(), request.unit(), request.active());
        tariffs.set(index, tariff);
        record(tariff, TariffChange.Type.UPDATED);
        return tariff;
    }

    // Si el estado no cambia, no se registra historial.
    public synchronized Tariff setActive(Long id, boolean active) {
        int index = indexOf(id);
        Tariff current = tariffs.get(index);
        if (current.active() == active) {
            return current;
        }
        Tariff tariff = new Tariff(id, current.name(), current.value(), current.unit(), active);
        tariffs.set(index, tariff);
        record(tariff, active ? TariffChange.Type.ACTIVATED : TariffChange.Type.DEACTIVATED);
        return tariff;
    }

    // Del más reciente al más antiguo.
    public synchronized List<TariffChange> history() {
        List<TariffChange> changes = new ArrayList<>(history);
        Collections.reverse(changes);
        return changes;
    }

    private Tariff add(String name, BigDecimal value, String unit, boolean active) {
        Tariff tariff = new Tariff(++lastId, name, value, unit, active);
        tariffs.add(tariff);
        return tariff;
    }

    private void record(Tariff tariff, TariffChange.Type type) {
        history.add(new TariffChange(tariff.id(), tariff.name(), type, Instant.now()));
    }

    private int indexOf(Long id) {
        for (int i = 0; i < tariffs.size(); i++) {
            if (tariffs.get(i).id().equals(id)) {
                return i;
            }
        }
        throw new TariffNotFoundException(id);
    }
}
