package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.calificacion.*;
import com.sise.GestionMercadoMayorista.service.CalificacionService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    // 1) Registrar calificación (con o sin login)
    @PostMapping
    public ResponseEntity<CalificacionResponseDto> registrar(
            @RequestBody CalificacionCrearRequestDto dto
    ) {
        CalificacionResponseDto resp = calificacionService.registrarCalificacion(dto);
        return ResponseEntity.ok(resp);
    }

    // 2) Promedio por stand
    @GetMapping("/stand/{idStand}/promedio")
    public ResponseEntity<PromedioCalificacionDto> promedio(
            @PathVariable Integer idStand
    ) {
        PromedioCalificacionDto dto = calificacionService.obtenerPromedioPorStand(idStand);
        return ResponseEntity.ok(dto);
    }

    // 3) Comentarios recientes por stand
    //    GET /api/public/calificaciones/stand/{idStand}/comentarios?page=0&size=10
    @GetMapping("/stand/{idStand}/comentarios")
    public ResponseEntity<Page<CalificacionResponseDto>> comentarios(
            @PathVariable Integer idStand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CalificacionResponseDto> result =
                calificacionService.listarComentariosPorStand(idStand, page, size);
        return ResponseEntity.ok(result);
    }
}