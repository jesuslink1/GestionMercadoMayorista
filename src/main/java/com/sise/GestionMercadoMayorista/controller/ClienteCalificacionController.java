package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.calificacion.*;
import com.sise.GestionMercadoMayorista.service.CalificacionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cliente/calificaciones")
@SecurityRequirement(name = "bearerAuth")
public class ClienteCalificacionController {

    private final CalificacionService calificacionService;

    public ClienteCalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    // Crear reseña (solo cliente logueado)
    @PostMapping
    public ResponseEntity<CalificacionResponseDto> crear(@RequestBody ClienteCalificacionCrearRequestDto dto) {
        CalificacionResponseDto resp = calificacionService.registrarCalificacionCliente(dto);
        return ResponseEntity.ok(resp);
    }

    // Mis reseñas (para PerfilUsuario > pestaña Comentarios)
    @GetMapping
    public ResponseEntity<Page<CalificacionResponseDto>> misCalificaciones(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(calificacionService.listarMisCalificaciones(page, size));
    }
}
