package com.parkingadmin.controller;

import com.parkingadmin.dto.DailyReport;
import com.parkingadmin.service.ReportService;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reports;

    public ReportController(ReportService reports) {
        this.reports = reports;
    }

    @GetMapping("/daily")
    public DailyReport daily() {
        return reports.daily();
    }

    // Exporta el reporte diario en CSV (una fila de datos).
    @GetMapping("/export")
    public ResponseEntity<String> export(@RequestParam @Pattern(regexp = "csv") String format) {
        DailyReport report = reports.daily();
        String csv = "fecha,ingresos,entradas,salidas,pagos\n"
                + report.date() + "," + report.revenue().toPlainString() + ","
                + report.entries() + "," + report.exits() + "," + report.payments() + "\n";
        return ResponseEntity.ok()
                .contentType(new MediaType("text", "csv", StandardCharsets.UTF_8))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"reporte-diario-" + report.date() + ".csv\"")
                .body(csv);
    }
}
