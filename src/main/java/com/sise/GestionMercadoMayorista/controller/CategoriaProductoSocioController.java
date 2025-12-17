package com.sise.GestionMercadoMayorista.controller;

import com.sise.GestionMercadoMayorista.dto.categoriaProducto.CategoriaProductoResponse;
import com.sise.GestionMercadoMayorista.service.CategoriaProductoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/socio/categorias-productos")
@SecurityRequirement(name = "bearerAuth")
public class CategoriaProductoSocioController {

    private final CategoriaProductoService categoriaProductoService;

    public CategoriaProductoSocioController(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }

    @GetMapping("/disponibles")
    @PreAuthorize("hasRole('SOCIO')")
    public ResponseEntity<List<CategoriaProductoResponse>> listarDisponibles() {
        return ResponseEntity.ok(categoriaProductoService.listarDisponiblesParaSocio());
    }
}