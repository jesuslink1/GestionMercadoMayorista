package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.categoriaStand.CategoriaStandRequest;
import com.sise.GestionMercadoMayorista.dto.categoriaStand.CategoriaStandResponse;
import com.sise.GestionMercadoMayorista.service.CategoriaStandService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categorias-stands")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaStandController {

    private final CategoriaStandService categoriaStandService;

    public CategoriaStandController(CategoriaStandService categoriaStandService) {
        this.categoriaStandService = categoriaStandService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoriaStandResponse>> listar() {
        return ResponseEntity.ok(categoriaStandService.listarTodas());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaStandResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaStandService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaStandResponse> crear(@RequestBody CategoriaStandRequest request) {
        return ResponseEntity.ok(categoriaStandService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaStandResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody CategoriaStandRequest request
    ) {
        return ResponseEntity.ok(categoriaStandService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaStandService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
