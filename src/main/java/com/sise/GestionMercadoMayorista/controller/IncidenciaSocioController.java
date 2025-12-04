package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.incidencia.*;
import com.sise.GestionMercadoMayorista.service.IncidenciaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/socio/incidencias")
@SecurityRequirement(name = "bearerAuth")
public class IncidenciaSocioController {

    private final IncidenciaService incidenciaService;

    public IncidenciaSocioController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @PostMapping
    public ResponseEntity<IncidenciaResponseDto> crearIncidencia(
            @RequestBody IncidenciaCrearRequestDto dto
    ) {
        IncidenciaResponseDto resp = incidenciaService.crearIncidencia(dto);
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    public ResponseEntity<Page<IncidenciaResponseDto>> listarMisIncidencias(
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<IncidenciaResponseDto> resp =
                incidenciaService.listarMisIncidencias(estado, page, size);
        return ResponseEntity.ok(resp);
    }
}