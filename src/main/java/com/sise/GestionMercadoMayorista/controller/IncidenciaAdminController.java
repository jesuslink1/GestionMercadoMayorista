package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.incidencia.*;
import com.sise.GestionMercadoMayorista.service.IncidenciaService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/incidencias")
public class IncidenciaAdminController {

    private final IncidenciaService incidenciaService;

    public IncidenciaAdminController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @GetMapping
    public ResponseEntity<Page<IncidenciaResponseDto>> listarIncidencias(
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String prioridad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<IncidenciaResponseDto> resp =
                incidenciaService.listarIncidencias(estado, tipo, prioridad, page, size);
        return ResponseEntity.ok(resp);
    }

    @PatchMapping("/{idIncidencia}/responsable")
    public ResponseEntity<IncidenciaResponseDto> asignarResponsable(
            @PathVariable Integer idIncidencia,
            @RequestBody IncidenciaAsignarResponsableRequestDto dto
    ) {
        IncidenciaResponseDto resp =
                incidenciaService.asignarResponsable(idIncidencia, dto.getIdResponsable());
        return ResponseEntity.ok(resp);
    }

    @PatchMapping("/{idIncidencia}/estado")
    public ResponseEntity<IncidenciaResponseDto> cambiarEstado(
            @PathVariable Integer idIncidencia,
            @RequestBody IncidenciaCambiarEstadoRequestDto dto
    ) {
        IncidenciaResponseDto resp =
                incidenciaService.cambiarEstado(idIncidencia, dto.getNuevoEstado());
        return ResponseEntity.ok(resp);
    }

    // Reporte: incidencias por mes
    @GetMapping("/reportes/mensual")
    public ResponseEntity<List<IncidenciaResumenMensualDto>> resumenMensual() {
        List<IncidenciaResumenMensualDto> resp = incidenciaService.resumenMensual();
        return ResponseEntity.ok(resp);
    }

    // Reporte: incidencias resueltas por responsable
    @GetMapping("/reportes/responsables")
    public ResponseEntity<List<IncidenciaPorResponsableDto>> resumenPorResponsable() {
        List<IncidenciaPorResponsableDto> resp = incidenciaService.resumenPorResponsable();
        return ResponseEntity.ok(resp);
    }
}
