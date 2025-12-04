package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.stand.CambiarEstadoStandRequest;
import com.sise.GestionMercadoMayorista.dto.stand.StandRequestDto;
import com.sise.GestionMercadoMayorista.dto.stand.StandResponseDto;
import com.sise.GestionMercadoMayorista.security.CustomUserDetails;
import com.sise.GestionMercadoMayorista.service.StandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/v1/stands")
public class StandController {

    private final StandService standService;

    public StandController(StandService standService) {
        this.standService = standService;
    }

    // ADMIN / SUPERVISOR – Listar con filtros
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<List<StandResponseDto>> listar(
            @RequestParam(required = false) String bloque,
            @RequestParam(required = false) Integer idCategoriaStand,
            @RequestParam(required = false) String estado
    ) {
        List<StandResponseDto> lista;

        // Si no hay filtros → usa el listarTodosActivos que ya tenías
        if (bloque == null && idCategoriaStand == null &&
                (estado == null || estado.isBlank())) {
            lista = standService.listarTodosActivos();
        } else {
            lista = standService.listarConFiltros(bloque, idCategoriaStand, estado);
        }

        return ResponseEntity.ok(lista);
    }

    // ADMIN / SUPERVISOR – Obtener detalle por ID
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<StandResponseDto> obtenerPorId(@PathVariable Integer id) {
        StandResponseDto dto = standService.obtenerPorId(id);
        return ResponseEntity.ok(dto);
    }


    // ADMIN / SUPERVISOR – Crear Stand
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<StandResponseDto> crear(@RequestBody StandRequestDto request) {
        StandResponseDto creado = standService.crear(request);
        return ResponseEntity.ok(creado);
    }


    // ADMIN / SUPERVISOR – Actualizar datos
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<StandResponseDto> actualizar(
            @PathVariable Integer id,
            @RequestBody StandRequestDto request) {

        StandResponseDto actualizado = standService.actualizar(id, request);
        return ResponseEntity.ok(actualizado);
    }


    // ADMIN / SUPERVISOR – Cambiar estado (ABIERTO/CERRADO/CLAUSURADO)
    @PatchMapping("/{id}/estado")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<Void> cambiarEstado(
            @PathVariable Integer id,
            @RequestBody CambiarEstadoStandRequest request
    ) {
        standService.cambiarEstado(id, request.getEstado());
        return ResponseEntity.noContent().build();
    }


    // ADMIN / SUPERVISOR – Eliminación lógica
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','SUPERVISOR')")
    public ResponseEntity<Void> eliminarLogico(@PathVariable Integer id) {
        standService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }

    // SOCIO – Ver solo sus propios stands
    @GetMapping("/mis-stands")
    @PreAuthorize("hasRole('SOCIO')")
    public ResponseEntity<List<StandResponseDto>> listarMisStands(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Integer idUsuario = userDetails.getUsuario().getId();
        List<StandResponseDto> lista = standService.listarPorPropietario(idUsuario);
        return ResponseEntity.ok(lista);
    }
}
