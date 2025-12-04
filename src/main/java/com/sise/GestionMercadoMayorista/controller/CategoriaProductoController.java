package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.categoriaProducto.CategoriaProductoRequest;
import com.sise.GestionMercadoMayorista.dto.categoriaProducto.CategoriaProductoResponse;
import com.sise.GestionMercadoMayorista.service.CategoriaProductoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/categorias-productos")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;

    public CategoriaProductoController(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<CategoriaProductoResponse>> listar() {
        return ResponseEntity.ok(categoriaProductoService.listarTodas());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaProductoResponse> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(categoriaProductoService.obtenerPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaProductoResponse> crear(@RequestBody CategoriaProductoRequest request) {
        return ResponseEntity.ok(categoriaProductoService.crear(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoriaProductoResponse> actualizar(
            @PathVariable Integer id,
            @RequestBody CategoriaProductoRequest request
    ) {
        return ResponseEntity.ok(categoriaProductoService.actualizar(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        categoriaProductoService.eliminarLogico(id);
        return ResponseEntity.noContent().build();
    }
}
