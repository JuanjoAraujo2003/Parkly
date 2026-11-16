package com.parkingadmin.parking;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api")
public class ParkingController {
    private final List<Tariff> tariffs = new CopyOnWriteArrayList<>(List.of(
            new Tariff("Hora estándar", new BigDecimal("4500"), "por hora", true),
            new Tariff("Fracción", new BigDecimal("2500"), "hasta 30 min", true),
            new Tariff("Tarifa nocturna", new BigDecimal("18000"), "de 20:00 a 06:00", true)
    ));

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        List<ParkingSpace> spaces = spaces();
        long occupied = spaces.stream().filter(space -> space.getStatus() == SpaceStatus.OCCUPIED).count();
        long reserved = spaces.stream().filter(space -> space.getStatus() == SpaceStatus.RESERVED).count();
        long available = spaces.stream().filter(space -> space.getStatus() == SpaceStatus.AVAILABLE).count();
        long maintenance = spaces.stream().filter(space -> space.getStatus() == SpaceStatus.MAINTENANCE).count();

        return Map.of(
                "spaces", spaces,
                "summary", Map.of(
                        "total", spaces.size(),
                        "occupied", occupied,
                        "reserved", reserved,
                        "available", available,
                        "maintenance", maintenance,
                        "occupancy", Math.round((occupied + reserved) * 100.0 / spaces.size()),
                        "revenue", 184250,
                        "activeVehicles", occupied
                ),
                "activity", List.of(
                        Map.of("vehicle", "JTG-482", "action", "Ingreso registrado", "time", "Hace 8 min", "type", "entry"),
                        Map.of("vehicle", "KLM-091", "action", "Reserva confirmada", "time", "Hace 14 min", "type", "reservation"),
                        Map.of("vehicle", "PQR-735", "action", "Pago completado", "time", "Hace 22 min", "type", "payment"),
                        Map.of("vehicle", "BCA-204", "action", "Salida registrada", "time", "Hace 31 min", "type", "exit")
                )
        );
    }

    @GetMapping("/tariffs")
    public List<Tariff> tariffs() {
        return tariffs;
    }

    @PostMapping("/tariffs")
    @ResponseStatus(HttpStatus.CREATED)
    public Tariff createTariff(@Valid @RequestBody Tariff tariff) {
        tariffs.add(tariff);
        return tariff;
    }

    private List<ParkingSpace> spaces() {
        return List.of(
                new ParkingSpace("A-01", "A", SpaceStatus.OCCUPIED, "JTG-482", "08:42"),
                new ParkingSpace("A-02", "A", SpaceStatus.AVAILABLE, null, null),
                new ParkingSpace("A-03", "A", SpaceStatus.RESERVED, "KLM-091", "09:15"),
                new ParkingSpace("A-04", "A", SpaceStatus.OCCUPIED, "PQR-735", "07:58"),
                new ParkingSpace("A-05", "A", SpaceStatus.AVAILABLE, null, null),
                new ParkingSpace("A-06", "A", SpaceStatus.MAINTENANCE, null, null),
                new ParkingSpace("B-01", "B", SpaceStatus.OCCUPIED, "MNO-619", "08:21"),
                new ParkingSpace("B-02", "B", SpaceStatus.AVAILABLE, null, null),
                new ParkingSpace("B-03", "B", SpaceStatus.OCCUPIED, "BCA-204", "06:47"),
                new ParkingSpace("B-04", "B", SpaceStatus.RESERVED, "DEF-580", "09:32"),
                new ParkingSpace("B-05", "B", SpaceStatus.AVAILABLE, null, null),
                new ParkingSpace("B-06", "B", SpaceStatus.AVAILABLE, null, null)
        );
    }
}
